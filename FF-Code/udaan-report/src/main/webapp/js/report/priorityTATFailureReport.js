var ERROR_FLAG="ERROR";

function clearFields() {	

	getDomElementById("stationList").options.length = 1;
	getDomElementById("branchList").options.length = 1;

}


function getStationsList(id) {
	

	clearFields();
	var region = document.getElementById('regionList').value;			
	if(region){
	var url = './stockTransferReport.do?submitName=getStations&region='
			+ region;
	}

	ajaxCallWithoutForm(url, populateStationList);

}
function populateStationList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('stationList');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
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




function clearScreen() {
	document.forms[0].reset();

}


function printReport(){	

	var msg = "";
	/*var branch = $("#branchList").val();
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();*/
	
	
	var region=$("#regionList" ).val();
	var station=$("#stationList" ).val();
	var branch=$("#branchList" ).val();
	var fromDate=$("#startDate" ).val();
	var toDate=$("#endDate" ).val();
		
	

	if ( (fromDate == null || fromDate.trim().length <= 0)
			|| (toDate == null || toDate.trim().length <= 0) || isNull(region) || 'select' ==  region.toLocaleLowerCase()) {

		if(isNull(region) || 'select' ==  region.toLocaleLowerCase())
			msg = msg + "Select Origin Region   \n";
			
		if (fromDate == null || fromDate.trim().length <= 0)
			msg = msg + "Select Period From  \n";

		if (toDate == null || toDate.trim().length <= 0)
			msg = msg + "Select Period To  \n";
		
		

		alert(msg);

	} else if (checkPriorityDate('fromDate', 'toDate')) {
		if(region=='Select'){
			region='';		
		}
		if(station.toLocaleLowerCase()=='select' || station.toLocaleLowerCase() =='all'){
			station='';		
		}
		if(branch.toLocaleLowerCase() =='select' || branch.toLocaleLowerCase() =='all'){
			branch='';		
		}
		var originRegionName = document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
 		var originStationName = document.getElementById('stationList').options[document.getElementById('stationList').selectedIndex].text;
 		var originBranchName = document.getElementById('branchList').options[document.getElementById('branchList').selectedIndex].text;
 		//For Not mandatory Fields
 		if(originStationName.toLocaleLowerCase()=='select' ){
 			originStationName='';		
		}
 		if(originBranchName.toLocaleLowerCase()=='select' ){
 			originBranchName='';		
		}
		var url = '/udaan-report/pages/reportviewer/priorityViewer.jsp?'
				+ "&branchID=" + branch + "&region=" + region + "&station="
				+ station + "&fromDate=" + fromDate + "&toDate=" + toDate
				 + "&originRegionName="+originRegionName
				 + "&originStationName="+originStationName
				 + "&originBranchName="+originBranchName;
		window.open(url, "_blank");
	}

 }


function checkPriorityDate(){
	var startDate=$("#startDate" ).val();
	var endDate=$("#endDate" ).val();
	var arrStartDate = startDate.split("/");	
	var date1 = new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[2], arrEndDate[1]-1, arrEndDate[0]);
	var today = new Date();
	if (date2 > today) {
		alert('Future Date cannot be entered');
		return false;
	}
	if(date1=="" || date1>date2){

		alert("Please ensure that end date is greater than or equal to start date");

		$("#startDate" ).val("");

		$("#startDate" ).focus();

		return false;

		}
	else {
		return true;
	}


}




