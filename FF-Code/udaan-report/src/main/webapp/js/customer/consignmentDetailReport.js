var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

function consignmentDetailReport() {
	var msg = "";
	var office = $("#branchList").val();
	var product = $("#product").val();
	var reportType = $("#Status").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var customerId = $("#loggedInCustomer").val();
	

	if (office == null || 'select' == office.toLocaleLowerCase()) {
		msg = msg + "Select Office \n";
	}else if (isNull(product)) {
		msg = msg + "Select Product \n";
	}else if (fromDate == null || $.trim(fromDate).length <= 0) {
		msg = msg + "Select From Date \n";
	}else if (toDate == null || $.trim(toDate).length <= 0) {
		msg = msg + "Select To Date \n";
	} else if (isNull(reportType)) {
		msg = msg + "Select Status Type \n";
	}
	if (!isNull(msg)) {
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {

		if (isNull(product)) {
			product = "All";
		}
		var officeName = document.getElementById('branchList').options[document
				.getElementById('branchList').selectedIndex].text;
		var reportTypeName = document.getElementById('Status').options[document
				.getElementById('Status').selectedIndex].text;
		var productName = $("#product option:selected").map(function() {
			return this.text;
		}).get().join(", ");

		var url = '/udaan-report/pages/reportviewer/ConsignmentDetailReportViewer.jsp?'
				+ "&product="
				+ product
				+ "&office="
				+ office
				+ "&officeName="
				+ officeName
				+ "&fromDate="
				+ fromDate
				+ "&toDate="
				+ toDate
				+ "&reportTypeName="
				+ reportTypeName
				+ "&productName="
				+ productName
				+ "&reportType="
				+ reportType
				+ "&customerId="
				+ customerId;

		window.open(url, "_blank");
	}

}

function checkDate(fromDate, toDate) {
	var startDate = $("#" + fromDate).val();
	var endDate = $("#" + toDate).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1] - 1, arrEndDate[0]);

	var today = new Date();
	if (date2 > today) {
		alert('Future Date cannot be entered');
		return false;
	}

	if (date1 == "" || date1 > date2) {

		alert("Please ensure that end date is greater than or equal to start date");

		$("#" + fromDate).val("");

		$("#" + fromDate).focus();

		$("#" + toDate).val("");

		// $("#" + toDate).focus();

		return false;

	} else {
		return true;
	}
}

function consignmentDetailReportFFCL() {
	var msg = "";
	var region = $("#regionList").val();
	var regionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;

	var station = $("#stationList").val();
	var stationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;

	var branch = $("#branchList").val();
	var branchName = document.getElementById('branchList').options[document
			.getElementById('branchList').selectedIndex].text;
	var product = $("#product").val();
	var reportType = $("#Status").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var customerId = $("#client").val();
	var customerName = document.getElementById('client').options[document
	                                                     			.getElementById('client').selectedIndex].text;
	

	if (region == null || 'select' == region.toLocaleLowerCase()) {
		msg = msg + "Select Origin Region \n";
	}

	else if (station == null || 'select' == station.toLocaleLowerCase()) {
		msg = msg + "Select Origin Station \n";
	}

	else if (isNull(branch) || 'select' == branch.toLocaleLowerCase()) {
		msg = msg + "Select Origin Branch \n";
	}
	else if (isNull(product)) {
		msg = msg + "Select Product \n";
	}else if (fromDate == null || $.trim(fromDate).length <= 0) {
		msg = msg + "Select From Date \n";
	}else if (toDate == null || $.trim(toDate).length <= 0) {
		msg = msg + "Select To Date \n";
	} else if (isNull(reportType)) {
		msg = msg + "Select Status Type \n";
	}
	if (!isNull(msg)) {
		alert(msg);

	} else if (checkDate('fromDate', 'toDate')) {

		if (isNull(product)) {
			product = "All";
		}
		var reportTypeName = document.getElementById('Status').options[document
				.getElementById('Status').selectedIndex].text;
		var productName = $("#product option:selected").map(function() {
			return this.text;
		}).get().join(", ");

		var url = '/udaan-report/pages/reportviewer/ConsignmentDetailReportViewerFFCL.jsp?'
				+"&region="
				+ region
				+"&regionName="
				+ regionName
				+"&station="
				+ station
				+"&stationName="
				+ stationName
				+"&branch="
				+ branch
				+"&branchName="
				+ branchName
				+ "&product="
				+ product
				+ "&fromDate="
				+ fromDate
				+ "&toDate="
				+ toDate
				+ "&reportTypeName="
				+ reportTypeName
				+ "&productName="
				+ productName
				+ "&reportType="
				+ reportType
				+ "&customerId="
				+ customerId
				+"&customerName="
				+ customerName;

		window.open(url, "_blank");
	}

}