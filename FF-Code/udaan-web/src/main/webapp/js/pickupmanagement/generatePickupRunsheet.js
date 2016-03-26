var pkupRunsheetHeaderIds = "";
var count = 0;
// Get run sheets for all HUB employees for current date
$(document).ready(function() {
	var oTable = $('#dataGrid').dataTable({
		"sScrollY" : "260",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
	setDataTableDefaultWidth();
});

function viewGeneratePickupRunSheetForBranchAtHub(){
	var url = './generatePickupRunsheet.do?submitName=viewGeneratePickupRunSheet';
	document.pickupRunsheetForm.action = url;
	document.pickupRunsheetForm.submit();
}
function deleteTableData() {
	var tableId="dataGrid";		
	$('#'+tableId).dataTable().fnClearTable();
	document.getElementById("chk0").checked = false;
}

//Search Button action
function searchAssignedRunsheets(){
	deleteTableData();
	showProcessing();
	var url="./generatePickupRunsheet.do?submitName=searchAssignedRunsheet";
	jQuery.ajax({
		url : url,
		type : "POST",
		data : jQuery("#pickupRunsheetForm").serialize(),
		success : function(req) {
			printCallBackAssignedRunsheets(req);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			jQuery.unblockUI();
			alert("Server error : " + errorThrown);
		}
	});
}

function addRows(rowcount){
	$('#dataGrid').dataTable().fnAddData(
	[
	 '<input type="hidden" name="to.generate" id="generate' + rowcount+ '" value="N"/><input type="checkbox" id="chk' + rowcount+ '" name="generateChk" onclick="isChecked(this,'+ rowcount+')"/>',
	 '<div id="srNo' + rowcount + '"></div>',
	 '<input type="hidden" name="to.pickupRunsheetHeaderIds" id="pkupRunsheetHeader' + rowcount+ '" /><input type="hidden" name="to.pkupAssignmentHeaderId" id="pkupAssignmentHeader' + rowcount+ '" /><input type="hidden" name="to.pkupAssignmentDtlId" id="pkupAssignmentDtlId' + rowcount+ '"/><div id="runsheetNo' + rowcount + '"></div>',
	 '<input type="hidden" name="to.employeeIds" id="employeeId' + rowcount+ '"/> <div id="employeeName' + rowcount + '"></div>',
	 '<input type="hidden" name="to.runsheetStatus" id="runsheetStatus' + rowcount+ '"/><div id="status' + rowcount + '"></div>',
	 '<div id="runsheetAssgnType' + rowcount + '"></div>',
	]);	
}

function printCallBackAssignedRunsheets(data){
	var runsheetToJsonArray = new Array();	
	if(!isNull(data)){
		runsheetToJsonArray = data.split("~");
		runsheetTOJson = runsheetToJsonArray[1];
		//On Generate Action
		if (!isNull(runsheetTOJson)) {
			var runsheetHeaderTO = eval('(' + runsheetTOJson + ')');
			if(!isNull(runsheetHeaderTO.runsheetTO.transactionMsg))
			alert(runsheetHeaderTO.runsheetTO.transactionMsg);
		}
		detailTOJson = runsheetToJsonArray[2];
		if (!isNull(detailTOJson)) {
			var runsheetDetailTO = eval('(' + detailTOJson + ')');
			for(var cnt=1;cnt <= runsheetDetailTO.detailTO.length;cnt++){
				addRows(cnt);
				if(runsheetDetailTO.detailTO[cnt-1].runsheetStatus == 'GENERATE'){
					document.getElementById("chk" +cnt).disabled=true;
				}
				getDomElementById("srNo"+cnt).innerHTML=cnt;
				getDomElementById("pkupRunsheetHeader" +cnt).value=runsheetDetailTO.detailTO[cnt-1].runsheetHeaderId;
				getDomElementById("pkupAssignmentHeader" +cnt).value=runsheetDetailTO.detailTO[cnt-1].assignmentHeaderId;
				getDomElementById("pkupAssignmentDtlId" +cnt).value=runsheetDetailTO.detailTO[cnt-1].assignmentDtlsIds;
				getDomElementById("runsheetNo"+cnt).innerHTML='<a id="runsheetNo'+ cnt+'" href="#" onclick="getPickupRunsheetDetails('+"'" + runsheetDetailTO.detailTO[cnt-1].runsheetNo + "'"+');">'+runsheetDetailTO.detailTO[cnt-1].runsheetNo+'</a>';
				getDomElementById("employeeId" +cnt).value=runsheetDetailTO.detailTO[cnt-1].employeeFieldStaffId;
				getDomElementById("employeeName"+cnt).innerHTML=runsheetDetailTO.detailTO[cnt-1].employeeFieldStaffName;
				getDomElementById("runsheetStatus" +cnt).value=runsheetDetailTO.detailTO[cnt-1].runsheetStatus;
				if(runsheetDetailTO.detailTO[cnt-1].runsheetStatus == UNUSED_STATUS){
					getDomElementById("status"+cnt).innerHTML="<b>"+runsheetDetailTO.detailTO[cnt-1].runsheetStatus+"</b>";
				}
				if(runsheetDetailTO.detailTO[cnt-1].runsheetStatus == OPEN_STATUS){
					getDomElementById("status"+cnt).innerHTML="<b><font color='FFA500'>"+runsheetDetailTO.detailTO[cnt-1].runsheetStatus+"</font></b>";
				}
				if(runsheetDetailTO.detailTO[cnt-1].runsheetStatus == UPDATED_STATUS){
					getDomElementById("status"+cnt).innerHTML="<b><font color='00008B'>"+runsheetDetailTO.detailTO[cnt-1].runsheetStatus+"</font></b>";
				}
				if(runsheetDetailTO.detailTO[cnt-1].runsheetStatus == CLOSED_STATUS){
					getDomElementById("status"+cnt).innerHTML="<b><font color='008000'>"+runsheetDetailTO.detailTO[cnt-1].runsheetStatus+"</font></b>";
				}
				getDomElementById("runsheetAssgnType"+cnt).innerHTML=runsheetDetailTO.detailTO[cnt-1].pickupAssignmentType;
			}		
		}else{
			var result=jsonJqueryParser(data);
			if(!isNull(result)&&!isNull(result[ERROR_FLAG])){
				alert(result[ERROR_FLAG]);
			}
		}		
	}
	hideProcessing();
}

//Generate Button action
function generatePickupRunsheet() {
	if (isCheckBoxSelected('generateChk')) {
		checkBoxEnable();
		var url = './generatePickupRunsheet.do?submitName=generatePickupRunsheet';
		showProcessing();
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#pickupRunsheetForm").serialize(),
			success : function(req) {
				deleteTableData();
				printCallBackAssignedRunsheets(req);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				jQuery.unblockUI();
				alert("Server error : " + errorThrown);
			}
		});		
	}
}

function isCheckBoxSelected(chkboxElem) {
	var checkFalg = 0;
	var runsheetStatus = "";
	var isRunsheetAlreadyGen = "N";
	var box = document.getElementsByName(chkboxElem);
	var count = box.length-1;
	for ( var i = 1; i <= count; i++) {
		if (box[i].checked) {
			var k=i;
			runsheetStatus = getValueByElementId("runsheetStatus" + k);
			if (!isNull(runsheetStatus) && runsheetStatus != UNUSED_STATUS) {
				isRunsheetAlreadyGen = "Y";
				document.getElementById("chk" + k).checked = false;
				document.getElementById("generate" + k).value = "N";
			} else {
				checkFalg = 1;
			}
		} else if (checkFalg != 1) {
			checkFalg = 0;
		}
	}
	if (isRunsheetAlreadyGen == "Y" && checkFalg == 1) {
		alert("Some of the selected runsheet(s) is already generated, hence will not considered");
	} else if (isRunsheetAlreadyGen == "Y") {
		document.getElementById("chk0").checked = false;
		alert("Selected runsheet(s) are already generated");		
		return false;
	} else if (checkFalg == 0) {
		//artf3106660 : Incorrect error message “CheckBox is not selected”
		alert('Please select runsheet to generate');
		return false;
	}
	return true;
}
function checkBoxEnable() {
	var chkBoxes = "";
	chkBoxes = document.pickupRunsheetForm.generateChk;
	var count = chkBoxes.length-1;
	for ( var cnt = 1; cnt <= count; cnt++) {
		document.getElementById('generate' + cnt).disabled = false;
	}
}

//Print Button action
function printPickupRunSheet() {
	if (isAtleastOneEmpSelected()) {
		if (isAddressChecked()) {
			document.getElementById("addressFlag").value = "Y";
		} else {
			document.getElementById("addressFlag").value = "N";
		}

		var url = "./generatePickupRunsheet.do?submitName=printGenPickupRunSheet&RunsheetHeaderIds="
				+ pkupRunsheetHeaderIds;
		ajaxCall(url, 'pickupRunsheetForm', printPku);
	} else {
		// Incorrect error message “No runsheet” when the user trying to clickon “Print” button without selecting any runsheet
		/*if (count == 0) {
			alert("No generated runsheets are found to print.");
		} else {*/
			alert("Please select atleast one runsheet.");
		//}
	}
}

function isAtleastOneEmpSelected() {
	pkupRunsheetHeaderIds = "";
	count = 0;
	var table = getDomElementById("dataGrid");
	var tableRowCount = table.rows.length;

	for ( var i = 1; i < tableRowCount; i++) {
		if (getDomElementById("chk" + i).checked == true) {
			if (document.getElementById("runsheetStatus" + i).value == 'Open'
					|| document.getElementById("runsheetStatus" + i).value == 'Updated') {
				if (isNull(pkupRunsheetHeaderIds)) {
					pkupRunsheetHeaderIds += document
							.getElementById("pkupRunsheetHeader" + i).value;
				} else {
					pkupRunsheetHeaderIds += ","
							+ document.getElementById("pkupRunsheetHeader" + i).value;
				}
				count = count + 1;
			}
		}
	}
	if (count > 0) {
		return true;
	} else {
		return false;
	}
}
function isAddressChecked() {
	var addChkBox = getDomElementById("address");
	if (addChkBox.checked) {
		return true;
	} else {
		return false;
	}
}
function printPku(data) {
	var url = "./generatePickupRunsheet.do?submitName=printGenPickup";
	window
			.open(
					url,
					'_blank',
					'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=800,height=800');
}

function getBranchEmployees() {
	var branchId = "";
	var url = "";
	branchId = document.getElementById("branch").value;
	if (branchId != null && branchId != "") {
		showProcessing();
		url = "./generatePickupRunsheet.do?submitName=getBranchEmployees&branch="
				+ branchId;
		jQuery.ajax({
			url : url,
			success : populateBranchEmployees
		});
	}
}
function populateBranchEmployees(data) {
	var employee = "";
	employee = document.getElementById("employee");
	employee.innerHTML = "";
	if (data != null && data != "") {
		var employeeDetails =jsonJqueryParser(data);
		if(employeeDetails[ERROR_FLAG]!=null){
			alert(employeeDetails[ERROR_FLAG]);
			jQuery.unblockUI();
			return;
		}
		createEmptyDropDownWithAllOption("employee");
		$.each(employeeDetails, function(index, value) {
			var employeeName=this.firstName+" "+this.lastName+" - " +this.empCode;
			addOptionTODropDown("employee", employeeName, this.employeeId);			
		});
		showDropDownBySelected("employee","-1");
	}
	jQuery.unblockUI();
}
function isChecked(obj, count) {
	if(count>0){
		if (obj.checked) {
			document.getElementById("generate" + count).value = "Y";
		} else {
			document.getElementById("generate" + count).value = "N";
		}
	}	
}
function checkAllChkboxes(ckbxsName, checkedVal) {
	var box = document.getElementsByName(ckbxsName);
	if (box != null && box.length > 0) {
		for ( var i = 0; i < box.length; i++) {
			if (checkedVal == true) {
				box[i].checked = true;
				isChecked(box[i],i);
			} else if (checkedVal == false) {
				box[i].checked = false;
			}
		}
	}
}
//Redirects the page to update pickup runsheet
function getPickupRunsheetDetails(RunsheetNo) {
	var url = "";
	if (RunsheetNo != null) {
		url = './updatePickupRunsheet.do?submitName=getPickupRunsheetDetails&RunsheetNo='
				+ RunsheetNo;
		showProcessing();
		document.pickupRunsheetForm.action = url;
		document.pickupRunsheetForm.submit();
	}
}