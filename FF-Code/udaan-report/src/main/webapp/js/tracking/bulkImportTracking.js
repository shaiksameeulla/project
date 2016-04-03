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
	document.bulkImportForm.action = "/udaan-report/bulkImportTracking.do?submitName=getBulkConsgDetails&type="+type;
	document.bulkImportForm.submit();

}

function clearScreen(action){
	var url = "./bulkImportTracking.do?submitName=viewBulkImportTracking";
	$('#example').dataTable().fnClearTable();
	submitForm(url, action);
}

function submitForm(url, action){
	if(confirm("Do you want to "+action+" details?")){
		
		document.bulkImportForm.action = url;
		document.bulkImportForm.submit();
	}
}

function getUploadList() {
	

	window.open('bulkImportTracking.do?submitName=getBulkUploadList');
}
