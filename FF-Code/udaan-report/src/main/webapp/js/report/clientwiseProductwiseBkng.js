var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

function clearScreen(){
	var url="./clientwiseProductwiseBkng.do?submitName=getClientWiseReport";
	submitTransfer(url,'Clear'); 
}
function submitTransfer(url,action){
	if(confirm("Do you want to "+action+" the details?")) {
		document.consignmentReportForm.action = url;
		document.consignmentReportForm.method = "post";
		document.consignmentReportForm.submit();
	}
}
function getStationsList(id, clearFields) {

	// $(id).empty();
	var region = $("#" + id).val();

	var url = './clientwiseProductwiseBkng.do?submitName=getStations&region='
			+region;

	ajaxCallWithoutForm(url, populateStationList);

}
function populateStationList(data) {

	if (!isNull(data)) {

		var responseText = data;

		var error = responseText[ERROR_FLAG];
		var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});
	}

}
function clearFields(clearFields) {
	var clearField = clearFields.split(",");
	for ( var i = 0; i < clearField.length; i++) {
		alert(clearField[i]);
	}
}
function getBranchList(id) {
	var cityID = $("#" + id).val();
	var url = './consignmentBookingDetailsReport.do?submitName=getBranchList&cityID='
			+ cityID;
	ajaxCallWithoutForm(url, populateBranchList);

}

function populateBranchList(data) {
	if (!isNull(data)) {

		var responseText = data;

		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('branchList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	}

}
function getStationsListAndPopulateDestination(id) {
	var region = $("#" + id).val();
	var url = './consignmentBookingDetailsReport.do?submitName=getStations&region='
			+ region;

	ajaxCallWithoutForm(url, populateDestinationStationList);

}
function populateDestinationStationList(data) {
	
	if (!isNull(data)) {

		var responseText = data;

		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('destStationList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});
	}

}
function showClientWiseBooking() {
	
	var msg = "";
	var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();
	var destRegionList = $("#destRegionList").val();
	var destStationList = $("#destStationList").val();


	if ((region == null || 'select' == region.toLocaleLowerCase())
			|| (station == null || 'select' == station.toLocaleLowerCase())
			|| (branch == null || 'select' == branch.toLocaleLowerCase())
			|| (destRegionList == null || 'select' == destRegionList
					.toLocaleLowerCase())
			|| (destStationList == null || 'select' == destStationList
					.toLocaleLowerCase())
			|| (fromDate == null || fromDate.trim().length <= 0)
			|| (toDate == null || toDate.trim().length <= 0)) {

		if (region == null || 'select' == region.toLocaleLowerCase()) {
			msg = msg + "Select Origin Region \n";
		}
		if (station == null || 'select' == station.toLocaleLowerCase()) {
			msg = msg + "Select Origin Station \n";
		}
		if (branch == null || 'select' == branch.toLocaleLowerCase()) {
			msg = msg + "Select Origin Branch \n";
		}

		if (destRegionList == null
				|| 'select' == destRegionList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Region \n";
		}

		if (destStationList == null
				|| 'select' == destStationList.toLocaleLowerCase()) {
			msg = msg + "Select Destination Station \n";
		}

		if (fromDate == null || fromDate.trim().length <= 0)
			fromDate = "Jan 01, 1990 01:00 AM";
		msg = msg + "Select From Date \n";

		if (toDate == null || toDate.trim().length <= 0)
			toDate = "Jan 01, 2030 01:00 AM";
		msg = msg + "Select To Date \n";

		alert(msg);

	} else {
		var url = '/udaan-report/pages/reportviewer/clientProductWiseBookingViewer.jsp?'
				+ "&branchID="
				+ branch
				+ "&region="
				+ region
				+ "&station="
				+ station
				+ "&destRegionList="
				+ destRegionList
				+ "&destStationList="
				+ destStationList
				+ "&fromDate="
				+ fromDate
				+ "&toDate=" + toDate;
		window.open(url, "_blank");
	}
}
