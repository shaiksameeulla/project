var ERROR_FLAG = "ERROR";

$(document).ready( function () {
				var oTable = $('#example').dataTable( {
					"sScrollY": "225",
					"sScrollX": "100%",
					"sScrollXInner":"320%",
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
					"iRightWidth": 10
				} );
				
				ajaxJquery("./bulkImportTracking.do?submitName=getTypeNames","bulkImportForm",getTypeNamesResponse);
			} );

function getTypeNamesResponse(ajaxResp) {
	if(!isNull(ajaxResp)){
		var data = jsonJqueryParser(ajaxResp);
		if (data[ERROR_FLAG] != null) {
			alert(data[ERROR_FLAG]);
			return;
		}
		populateDropDown(data);	
	}	
}
function populateDropDown(data) {
	var content = document.getElementById('typeName');
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");

		option.value = this.stdTypeCode;
		option.appendChild(document.createTextNode(this.description));
		content.appendChild(option);
	});
}

function upload() {

	var fileValue = document.getElementById('fileUpload').value;
	var type = document.getElementById("typeName").value;
	if (isNull(type)) {
		alert('Please select type');
		return false;
	}
	if (isNull(fileValue)) {
		alert('Please choose a file to upload');
		return false;
	}
	// validates the extension of the excel file
	else {
		var ext = fileValue.split(".");
		if (!(ext[1] == 'xls' || ext[1] == 'xlsx')) {
			alert('Files with only xls and xlsx format are allowed');
			return false;
		}
	}
	showProcessing();
	document.bulkImportForm.action = "/udaan-config-admin/bulkImportTracking.do?submitName=getBulkConsgDetails";
	document.bulkImportForm.submit();
}

function clearScreen(action){
	var url = "./bulkImportTracking.do?submitName=viewBulkImportTracking";
	submitForm(url, action);
}

function submitForm(url, action){
	if(confirm("Do you want to "+action+" details?")){
		document.bulkImportForm.action = url;
		document.bulkImportForm.submit();
	}
}

function getUploadList() {
	if(isFileUploaded){
		window.open('bulkImportTracking.do?submitName=getBulkUploadList');
	}else{
		alert("Please upload the consignments excel sheet to track.");
	}	
}
