function getSalesDetails() {
	var branch = $("#branchList").val();
	var fromDate = "01/"+$("#fromDate").val();
	var toDate = "31/"+$("#toDate").val();
	var region = $("#regionList").val();
	var station = $("#stationList").val();		
	
	var frommonth=$("#fromDate" ).val();
	var tomonth=$("#toDate" ).val();	
	
	var msg = "";
	
	if(isNull(region) || 'select' ==  region.toLocaleLowerCase() || 
			isNull(station) || 'select' ==  station.toLocaleLowerCase() ||
			isNull(branch) || 'select' ==  branch.toLocaleLowerCase() || isNull(frommonth) || isNull(tomonth)){		
		
		
		if(isNull(region) || 'select' ==  region.toLocaleLowerCase()){
			msg = msg + "Select Origin Region \n";
			
		}
		
		if(isNull(station) || 'select' ==  station.toLocaleLowerCase()){
			msg = msg + "Select Origin Station \n";
			
		}
		
		if(isNull(branch) || 'select' ==  branch.toLocaleLowerCase()){
			msg = msg + "Select Origin Branch \n";
			
		}
		
		if(isNull(frommonth) ){
			msg = msg + "Select From Date \n";
		
		}
		
		if(isNull(tomonth) ){
			msg = msg + "Select To Date \n";
			
		}
		
		alert(msg);
		
		
	
	}
	else {
		
		
		if(checkTargetDate('fromDate', 'toDate')){	
		
		var nwFromDate=formatDatetoStr(fromDate);
		var nwToDate=formatDatetoStr(toDate);
		
		if (region.toLocaleLowerCase() == 'select') {
			region = "";
		}
		if (station.toLocaleLowerCase() == 'select') {
			station = "";
		}
		if (branch.toLocaleLowerCase() == 'select') {
			branch = "";
		}
		
 		var url = '/udaan-report/pages/reportviewer/TargetVsActualSalesViewer.jsp?'
				+ "&branch=" + branch + "&region=" + region + "&station="
				+ station + "&fromDate=" + nwFromDate + "&toDate=" + nwToDate;
 		window.open(url, "_blank");
		}
 		
		
	}
}


function checkTargetDate(fromDate, toDate) {
	
	var startDate = $("#" + fromDate).val();
	var endDate = $("#" + toDate).val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[1], arrStartDate[0] - 1, 1);
	var arrEndDate = endDate.split("/");
	var date2 = new Date(arrEndDate[1], arrEndDate[0] - 1, 1);
	var today = new Date();
	if (date2 > today) {
		alert('Future Month cannot be entered');
		$("#" + toDate).val("");

		$("#" + toDate).focus();
		return false;
	}

	if (date1 == "" || date1 > date2) {

		alert("Please ensure that to Month is greater than or equal to from month");

		$("#" + fromDate).val("");

		$("#" + fromDate).focus();

		return false;

	} else {
		return true;
	}
}

function formatDatetoStr(str){
	var arrStartDate = str.split("/");
	return (arrStartDate[1]+"/"+ arrStartDate[0]+"/"+arrStartDate[2]);
}
function formatDate(str){
	var arrStartDate = str.split("/");
	return new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
}