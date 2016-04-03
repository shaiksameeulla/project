function getSalesPersonList(obj) {
	if (obj.value != "" && obj.value.toUpperCase() != "SELECT") {
		var branch = $("#branchList").val();
		var region = $("#regionList").val();
		var station = $("#stationList").val();
		if (region.toLocaleLowerCase() == 'select' || region.toLocaleLowerCase() == "all") {
			region = "";
		}
		if (station.toLocaleLowerCase() == 'select' || station.toLocaleLowerCase() == "all") {
			station = "";
		}
		if (branch.toLocaleLowerCase() == 'select' || branch.toLocaleLowerCase() == "all") {
			branch = "";
		}
		$("#salesPerson").empty();
		var url = './clientGained.do?submitName=getSalesPersonList&region='
				+ region + "&branch=" + branch + "&station=" + station;
		ajaxCallWithoutForm(url, callBackGetSalesList);
	}
}
function callBackGetSalesList(data){
	var content = document.getElementById('salesPerson');
	content.innerHTML = "";
	// Select
	var defOption = document.createElement("option");
	defOption.value = "All";
	defOption.appendChild(document.createTextNode("ALL"));
	content.appendChild(defOption);
	// All
	
	if (!isNull(data)) {
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.employeeId;
				option.appendChild(document.createTextNode(this.firstName + "-" + this.lastName));
				content.appendChild(option);
	});
   }
  }
function clientGainedDetails() {
	var branch = $("#branchList").val();
	var branchName = document.getElementById('branchList').options[document
	    	                                               			.getElementById('branchList').selectedIndex].text;
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var regionName = document.getElementById('regionList').options[document
	    	                                               			.getElementById('regionList').selectedIndex].text;
	var station = $("#stationList").val();
	var stationName = document.getElementById('stationList').options[document
		    	                                               			.getElementById('stationList').selectedIndex].text;
	var product = $("#product").val();
	var productName = document.getElementById('product').options[document
		    	                                               			.getElementById('product').selectedIndex].text;
	var salesPerson = $("#salesPerson").val();
	var salesPersonName = document.getElementById('salesPerson').options[document
		    	                                               			.getElementById('salesPerson').selectedIndex].text;
	
//	var reportType = $("#reportType").val();
//	var reportTypeName = document.getElementById('reportType').options[document
//		    	                                               			.getElementById('reportType').selectedIndex].text;
	
	
	if (region.toLocaleLowerCase() == 'select' ||region.toLocaleLowerCase() == 'all' || region.toLocaleLowerCase() == "") {
		region = "";
	}
	if (station.toLocaleLowerCase() == 'select' ||station.toLocaleLowerCase() == 'all' || region.toLocaleLowerCase() == "") {
		station = "";
	}
	if (branch.toLocaleLowerCase() == 'select' || branch.toLocaleLowerCase() == 'all' || region.toLocaleLowerCase() == "") {
		branch = "";
	}
	if (product.toLocaleLowerCase() == 'all' ||product.toLocaleLowerCase() == 'select') {
		product = "";
		productName = "All";
	}
	if (salesPerson.toLocaleLowerCase() == 'all' || salesPerson.toLocaleLowerCase() == 'select') {
		salesPerson = "";
		salesPersonName = "All";
	}	
//	if (reportType.toLocaleLowerCase() == 'all' || reportType.toLocaleLowerCase() == 'select') {
//		reportType = "";
//		reportTypeName = "All";
//		
//	}
	if(isNull(region)){
		alert('Select Mandatory Field');
		if(isNull(region)){
			$("#regionList").focus();
		}
	}
	else if(fromDate==null || fromDate.trim().length<=0){
		alert('Please Enter From Date');
		$("#fromDate").focus();
	}
	else if(toDate==null || toDate.trim().length<=0){
		alert('Please Enter To Date');
		$("#toDate").focus();
	}
	else if(formatDate(fromDate)>new Date()){
		alert("From Date Can Not Be Future Date");
		$("#fromDate").focus();
	}
	else if(formatDate(toDate)>new Date()){
		alert("To Date Can Not Be Future Date");
		$("#toDate").focus();
	}
	else if(formatDate(fromDate)>formatDate(toDate)){
		alert('Please Enter Valid Date Range');
		$("#fromDate").focus();
	}
	else {

		var url = '/udaan-report/pages/reportviewer/ClientGainedViewer.jsp?'
				+ "&branch="
				+ branch
				+ "&region="
				+ region
				+ "&station="
				+ station
				+ "&product="
				+ product
				+ "&salesPerson="
				+ salesPerson
				+ "&fromDate="
				+ fromDate
				+ "&toDate="
				+ toDate
				+ "&branchName="
				+ branchName
				+ "&regionName="
				+ regionName
				+ "&stationName="
				+ stationName
				+ "&productName="
				+ productName
				+ "&salesPersonName="
				+ salesPersonName;
		window.open(url, "_blank");
	}
}
function formatDate(str){
	var arrStartDate = str.split("/");
	return new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
}
/**
 * clearScreen
 * @param type
 */
function clearFilterScreen() {
	if(confirm("Do you want to clear the filter details?")) {
		var url="./clientGained.do?submitName=getClientGainedReport";
		globalFormSubmit(url,'consignmentReportForm');
	}
	
}
	
