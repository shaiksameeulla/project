/*function getStationsList(){

	var region = document.getElementById('region').value;
	$("#stationList" ).empty();
	var url = './lcTopayCodConsignmentDetailsReport.do?submitName=getStations&region='
		+ region;
	ajaxCallWithoutForm(url, getStationListData);
	
}

function getStationListData(data){
	if (!isNull(data)) {
			var content = document.getElementById('station');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
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
function getHubOfficeList(){
	
	var station = document.getElementById('station').value;
	$("#officeList" ).empty();
	var url = './lcTopayCodConsignmentDetailsReport.do?submitName=getHubOffices&station='
		+ station;
	ajaxCallWithoutForm(url, getHubOfficeListData);
	
	
}

function getHubOfficeListData(data){
	if (!isNull(data)) {
		   
			var content = document.getElementById('office');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeName));
				content.appendChild(option);
				
	});
   }
  }*/

function readLCBranch(){
	var branch = "";
	var branches = document.getElementById("branchList");	
	if($("#branchList").val().toUpperCase() == "ALL"){
		for (var i = 2; i<branches.length; i++){
			branch = branch + branches.options[i].value;
			
			if(i != (branches.length-1)){
				branch = branch + ", ";
			}
		}
	}else{
		branch = $("#branchList").val();
	}
	return branch;
}


function getClientList() {
	// var office = document.getElementById('branchList').value;
	// $("#client" ).empty();
	getDomElementById("client").options.length = 1;
	var office = readLCBranch();

	var regionId = getDomElementById("regionList");
	if (isNull(regionId.value) || regionId.value.toUpperCase() == "SELECT") {
		alert("Please select region");
		setTimeout(function() {
			regionId.focus();
		}, 10);
		return false;
	}
	
	if ($("#stationList").val().toUpperCase() != "ALL" 
			|| $("#branchList").val().toUpperCase() != "ALL") {
		regionId = "";
	}
	
	if ($("#branchList").val().toUpperCase() != "SELECT") {
		// var url =
		// './lcTopayCodConsignmentDetailsReport.do?submitName=getCustomerList&branch='
		// + office;
		var url = './lcTopayCodConsignmentDetailsReport.do?submitName=getCustomerListForReport&branch='
				+ office;
		if(!isNull(regionId)) {
			url += "&regionId=" + regionId.value;
		}
		showProcessing();
//		ajaxCallWithoutForm(url, getClientsListData);
		jQuery.ajax({
			url : url,
			type : "GET",
			dataType : "json",
			success : function(data) {
				getClientsListData(data);
			}
		});
	}
}

function getClientsListData(data) {
	if (!isNull(data)) {
		var content = document.getElementById('client');
		content.innerHTML = "";
		// Select
		var defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		// All
		var allOpt = document.createElement("option");
		allOpt.value = "All";
		allOpt.appendChild(document.createTextNode("All"));
		content.appendChild(allOpt);
		$.each(data, function(index, value) {
			if (this.businessName != undefined) {
				var option;
				option = document.createElement("option");
				option.value = this.customerId;
				option.appendChild(document.createTextNode(this.businessName + " - " + this.customerCode));
				content.appendChild(option);
			}
		});
	} else {
		var content = document.getElementById('client');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
	}
	hideProcessing();
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
function getLcTopayCodDetails() {
	var region = $("#regionList").val();
	var regionName = document.getElementById('regionList').options[document
	                                               			.getElementById('regionList').selectedIndex].text;
	var station = $("#stationList").val();
	var stationName = document.getElementById('stationList').options[document
	    	                                               			.getElementById('stationList').selectedIndex].text;
	var office = $("#branchList").val();
	var officeName = document.getElementById('branchList').options[document
	   	                                               			.getElementById('branchList').selectedIndex].text;
	var client = $("#client").val();
	var clientName = document.getElementById('client').options[document
	   	                                               			.getElementById('client').selectedIndex].text;
	if (region == "Select" || region == "All")
		region = "";
	if (station == "Select" || station == "All")
		station = "";
	if (office == "Select" || office == "All")
		office = "";
	if (client == "Select" || client == "All") {
		client = "";
		clientName = "All";
	}
	var fromdate = $("#fromdate").val();
	var todate = $("#todate").val();
	fromdate = getFormattedDate(fromdate);
	todate = getFormattedDate(todate);
	if (isNull(region)) {
		alert('Select Mandatory Field ');
		$("#regionList").focus();
	} else if (fromdate == null || fromdate == "") {
		alert('Please Select the Date Range');
		$("#fromdate").focus();
	} else if (todate == null || todate == "") {
		alert('Please Select the Date Range');
		$("#todate").focus();
	} else if (new Date(fromdate) > new Date()) {
		alert("Date Can Not Be Future Date");
		$("#fromdate").focus();
	} else if (new Date(todate) > new Date()) {
		alert("Date Can Not Be Future Date");
		$("#todate").focus();
	} else if (new Date(fromdate) > new Date(todate)) {
		alert('Please Enter Valid Date Range');
		$("#fromdate").focus();
	} else {
		if (fromdate == todate)
			todate = todate.concat(" 11:59 PM");
		var url = '/udaan-report/pages/reportviewer/LcTopayCodConsignmentDetailsViewer.jsp?'
				+ "&region="
				+ region
				+ "&regionName="
				+ regionName
				+ "&station="
				+ station
				+ "&stationName="
				+ stationName
				+ "&office="
				+ office
				+ "&officeName="
				+ officeName
				+ "&client="
				+ client
				+ "&clientName="
				+ clientName
				+ "&fromdate="
				+ fromdate
				+ "&todate=" + todate;

		window.open(url, "_blank");
	}

}

/**
 * clearScreen
 * 
 * @param type
 */
function clearLcScreen() {
	
	var url="./lcTopayCodConsignmentDetailsReport.do?submitName=viewFormDetails";
	submitTransfer(url,'Clear'); 

}

function submitTransfer(url,action){
	if(confirm("Do you want to "+action+" the details?")) {
		document.consignmentReportForm.action = url;
		document.consignmentReportForm.method = "post";
		document.consignmentReportForm.submit();
	}
}
