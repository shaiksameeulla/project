function populateStationList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
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
function getFormattedDate(input) {
	var pattern = /(.*?)\/(.*?)\/(.*?)$/;
	var result = input.replace(pattern, function(match, p1, p2, p3) {
		var months = [ 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
				'Sep', 'Oct', 'Nov', 'Dec' ];
		return (months[(p2 - 1)] + " " + p1 + ", " + p3);
	});
	return result;
}
function getTptDetails() {
	var region = $("#regionList").val();
	var regionName = document.getElementById('regionList').options[document
			.getElementById('regionList').selectedIndex].text;
	var station = $("#stationList").val();
	var stationName = document.getElementById('stationList').options[document
			.getElementById('stationList').selectedIndex].text;
	
	var hub = $("#office").val();
	var hubName = document.getElementById('office').options[document
	                                                 			.getElementById('office').selectedIndex].text;
	
	var reportType = $("#reportType").val();
	var reportTypeName = document.getElementById('reportType').options[document
			.getElementById('reportType').selectedIndex].text;
	var fromdate = $("#fromDate").val();
	var todate = $("#toDate").val();
	fromdate = getFormattedDate(fromdate);
	todate = getFormattedDate(todate);
	if (region == "Select" || region == "All")
		region = "";
	if (station == "Select" || station == "All")
		station = "";
	if (reportType == "Select" || reportType == "All")
		reportType = "";
	if (isNull(region) || isNull(station) || isNull(reportType) || isNull(hub)) {
		alert('Select Mandatory Field ');
		if (isNull(region)) {
			$("#regionList").focus();
		} else if(isNull(station)){
			$("#stationList").focus();
		} else if(isNull(office)) {
			$("#office").focus();
		} else if(isNull(reportType)) {
			$("#reportType").focus();
		} else if(isNull(hub)) {
			$("#office").focus();
		}
	} else if (fromdate == null || fromdate.trim().length <= 0) {
		alert('Please Select the Date Range');
		$("#fromDate").focus();
	} else if (todate == null || todate.trim().length <= 0) {
		alert('Please Select the Date Range');
		$("#toDate").focus();
	} else if (new Date(getFormattedDate(fromdate)) > new Date()) {
		alert("Date Can Not Be Future Date");
		$("#fromDate").focus();
	} else if (new Date(getFormattedDate(todate)) > new Date()) {
		alert("Date Can Not Be Future Date");
		$("#toDate").focus();
	} else if (new Date(fromdate) > new Date(todate)) {
		alert('Please Enter Valid Date Range');
		$("#fromDate").focus();
	} else {
		if (fromdate == todate)
			todate = todate.concat(" 11:59 PM");
		var url = '/udaan-report/pages/reportviewer/TptLoadHandledViewer.jsp?'
				+ "&region=" + region + "&regionName=" + regionName
				+ "&station=" + station + "&stationName=" + stationName + "&hub=" + hub + "&hubName=" + hubName
				+ "&reportType=" + reportType + "&reportTypeName="
				+ reportTypeName + "&fromdate=" + fromdate + "&todate="
				+ todate;
		window.open(url, "_blank");
	}

}

/**
 * clearScreen
 * 
 * @param type
 */
function clearScreen() {
	if (confirm("Do you want to clear the filter details?")) {
		var url = "./tptLoadHandled.do?submitName=getTptLoadHandledReport";
		globalFormSubmit(url, 'consignmentReportForm');
	}

}

function getAllHubsByCity() {
	var station = document.getElementById('stationList').value;
	$("#officeList").empty();
	var url = './tptLoadHandled.do?submitName=getHubOffices&station='
			+ station;
	ajaxCallWithoutForm(url, getHubOfficeListData);
}

function getHubOfficeListData(data) {
	if (!isNull(data)) {

		var content = document.getElementById('office');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		/*
		 * var allOption; allOption = document.createElement("option");
		 * allOption.value = "";
		 * allOption.appendChild(document.createTextNode("All"));
		 * content.appendChild(allOption);
		 */
		$.each(data, function(index, value) {
			if (this.officeName != undefined) {
				noHubs = false;
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeName));
				content.appendChild(option);
			} else {
				noHubs = true;
			}
		});
	}
}
