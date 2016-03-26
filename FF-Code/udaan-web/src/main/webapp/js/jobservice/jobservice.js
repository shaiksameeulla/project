var SUCCESS_FLAG = "SUCCESS";
var ERROR_FLAG = "ERROR";

rowCount = 1;
function addRow(record) {

	var failureFile = "";
	var successFile = "";
	serialNo = 1;
	if (isNull(record.failureFile)) {
		failureFile = '-';
	} else {
		failureFile = '<a href="./jobService.do?submitName=downloadJobResponseFile&jobNumber='
				+ record.jobNumber + '&fileType=F">Download</a>';
	}
	if (isNull(record.successFile)) {
		successFile = '-';
	} else {
		successFile = '<a href="./jobService.do?submitName=downloadJobResponseFile&jobNumber='
				+ record.jobNumber + '&fileType=S">Download</a>';
	}

	$('#example').dataTable().fnAddData(
			[

					rowCount,
					'<span id="jobNumber">' + record.jobNumber + '</span>',
					'<span id="fileSubmissionDate">' + record.fileSubDate
							+ '</span>',
					'<span id="remarks">' + record.remarks + '</span>',
					'<span id="percentageCompleted">'
							+ record.percentageCompleted + '</span>',
					'<span id="jobStatus">' + getStatus(record.jobStatus)
							+ '</span>',
					'<span id="errorFile">' + failureFile + '</span>',
					'<span id="successFile">' + successFile + '</span>' ]);

	rowCount++;
}

function getJobServices() {

	// if(!isNull()){
	deleteTableRow();

	var toDate = "";
	var fromDate = "";
	var jobNumber = "";
	var processCode = "";

	if (!isNull(getDomElementById("jobNumber"))) {
		jobNumber = getDomElementById("jobNumber").value;
	}

	if (!isNull(getDomElementById("processCode"))) {
		processCode = getDomElementById("processCode").value;
	}

	if (!isNull(getDomElementById("fromDate"))) {
		fromDate = getDomElementById("fromDate").value;
	}

	if (!isNull(getDomElementById("toDate"))) {
		toDate = getDomElementById("toDate").value;
	}

	// if(validateDates()){
	var url = "./jobService.do?submitName=getJobServices&jobNumber="
			+ jobNumber + "&processCode=" + processCode + "&fromDate="
			+ fromDate + "&toDate=" + toDate;
	ajaxCallWithoutForm(url, showDetails);
	// /}
}

function showDetails(data) {
	deleteTableRow();
	if (!isNull(data)) {
		for ( var i = 0; i < data.length; i++) {
			addRow(data[i]);
		}
	}
}

function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

function deleteTableRow() {

	try {
		var table = getDomElementById("example");
		var tableRowCount = table.rows.length;

		for ( var i = 0; i < tableRowCount; i++) {
			deleteRow("example", i);
			tableRowCount--;
			i--;
		}
	} catch (e) {
		alert(e);
	}
	rowCount = 1;

}

function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
}

function addOptionTODropDown(selectId, label, value) {

	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);

}

function getList(fieldId) {
	var list = "";
	var optionsList = getDomElementById(fieldId).options;
	for ( var i = 1; i < optionsList.length; i++) {
		opt = document.createElement('OPTION');
		if (optionsList[i].value == "A")
			continue;
		else {
			if (list.length > 0)
				list = list + ",";
			list = list + optionsList[i].value;
		}
	}
	return list;
}

function validateFromDate(obj) {
	if (!isNull(obj.value)) {
		validDate = obj.value;
		var validDateDay = parseInt(validDate.substring(0, 2), 10);
		var validDateMon = parseInt(validDate.substring(3, 5), 10);
		var validDateYr = parseInt(validDate.substring(6, 10), 10);
		var fromDate = new Date(validDateYr, (validDateMon - 1), validDateDay);
		var toDayDate = new Date();
		toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(),
				toDayDate.getDate());
		if (fromDate > toDayDate) {
			alert("From Date should not be greater than today date");
			obj.value = "";
			return false;
		}
		return true;
	}
}

function validateToDate(obj) {
	var validfromDate = document.getElementById("fromDate").value;
	if (!isNull(obj.value) && !isNull(validfromDate)) {
		validDate = obj.value;
		var validDateDay = parseInt(validDate.substring(0, 2), 10);
		var validDateMon = parseInt(validDate.substring(3, 5), 10);
		var validDateYr = parseInt(validDate.substring(6, 10), 10);

		var validfromDateDay = parseInt(validfromDate.substring(0, 2), 10);
		var validfromDateMon = parseInt(validfromDate.substring(3, 5), 10);
		var validfromDateYr = parseInt(validfromDate.substring(6, 10), 10);

		var fromDate = new Date(validfromDateYr, (validfromDateMon - 1),
				validfromDateDay);
		var toDate = new Date(validDateYr, (validDateMon - 1), validDateDay);

		var toDayDate = new Date();
		toDayDate = new Date(toDayDate.getFullYear(), toDayDate.getMonth(),
				toDayDate.getDate());

		if (toDate > toDayDate) {
			alert("To Date should not be greater than today date");
			obj.value = "";
			return false;
		} else if (toDate < fromDate) {
			alert("To Date should be greater than From date");
			obj.value = "";
			return false;
		}

	} else if (isNull(validfromDate)) {
		alert("Please select From date");
		obj.value = "";
		return false;
	}

	return true;
}

function validateDates() {
	var validFromDate = document.getElementById("fromDate").value;
	var validToDate = document.getElementById("toDate").value;

	if (isNull(validFromDate)) {
		alert("Please select From date");
		return false;
	} else if (isNull(validToDate)) {
		alert("Please select To date");
		return false;
	} else if (!isNull(validFromDate) && !isNull(validToDate)) {
		if (!validateToDate(document.getElementById("toDate"))) {
			return false;
		}
	}
	return true;
}

function getJob() {
	var jobNumber = "";
	if (!isNull(getDomElementById("jobNumber"))) {
		jobNumber = getDomElementById("jobNumber").value;
		var url = "./jobService.do?submitName=getJobServices&jobNumber="
				+ jobNumber;
		ajaxCallWithoutForm(url, assignJobDetails);
	} else {
		getDomElementById("processImg").style.display = 'none';
	}
}

function assignJobDetails(data) {
	if (!isNull(data) && data.length > 0) {
		record = data[0];
		getDomElementById("jobNo").innerHTML = record.jobNumber;
		getDomElementById("jobSubmittedDate").innerHTML = record.fileSubDate;
		getDomElementById("percentageCompleted").innerHTML = record.percentageCompleted;
		getDomElementById("jobStatus").innerHTML = getStatus(record.jobStatus);
		if (isNull(record.failureFile)) {
			getDomElementById("errorFile").innerHTML = 'Not available';
		} else {
			getDomElementById("errorFile").innerHTML = '<a href="./jobService.do?submitName=downloadJobResponseFile&jobNumber='
					+ record.jobNumber + '&fileType=F">Download</a>';
		}

		if (isNull(record.successFile)) {
			getDomElementById("successFile").innerHTML = 'Not available';
		} else {
			getDomElementById("successFile").innerHTML = '<a href="./jobService.do?submitName=downloadJobResponseFile&jobNumber='
					+ record.jobNumber + '&fileType=S">Download</a>';
		}
		getDomElementById("remarks").innerHTML = record.remarks;
		if (record.percentageCompleted == 100) {
			getDomElementById("processImg").style.display = 'none';
			//***********Start *****new Code***********************
			//document.getElementById("bulk_print_btn").disabled=false;
			
			jQuery("#bulk_print_btn").attr("disabled", false);
			jQuery("#bulk_print_btn").removeClass("btnintformbigdis");
			jQuery("#bulk_print_btn").addClass("btnintform");

			//***********End********new Code***********************
		}
	} else {
		getDomElementById("jobNo").innerHTML = "";
		getDomElementById("jobSubmittedDate").innerHTML = "";
		getDomElementById("percentageCompleted").innerHTML = "";
		getDomElementById("jobStatus").innerHTML = "";
		getDomElementById("errorFile").innerHTML = "";
		getDomElementById("successFile").innerHTML = "";
		getDomElementById("remarks").innerHTML = "";
		getDomElementById("processImg").style.display = 'none';
	}
}

function getStatus(objVal) {
	if (objVal == "I") {
		return "In progress";
	}

	else if (objVal == "S") {
		return "Success";
	}

	else if (objVal == "P") {
		return "Partially success";
	}

	else if (objVal == "F") {
		return "Failed";
	}

	else if (objVal == "N") {
		return "Not started";
	} else {
		return "-";
	}
}
