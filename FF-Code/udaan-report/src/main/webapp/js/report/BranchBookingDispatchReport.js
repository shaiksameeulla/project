function getClientsList(){

	var office = document.getElementById('branchList').value;
	$("#clientList" ).empty();
	var url = './originHubTallyReport.do?submitName=getClients&office='
		+ office;
	ajaxCallWithoutForm(url, getClientsListData);
	
}

function getClientsListData(data){
	
	if (!isNull(data)) {
			   
			var content = document.getElementById('client');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				var val=this.businessName;
				if (val!=null && val.trim().length>0){
					option = document.createElement("option");
					option.value = this.customerId;
					option.appendChild(document.createTextNode(this.businessName));
					content.appendChild(option);
				}
	});
   }else{
	   var content = document.getElementById('client');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);  
   }
  }
function getFormattedDate(input){
    var pattern=/(.*?)\/(.*?)\/(.*?)$/;
    var result = input.replace(pattern,function(match,p1,p2,p3){
        var months=['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
        return (months[(p2-1)]+" "+p1+", "+p3);
    });
    return result;
}
function getBranchBookingDispatchReport() {
	var region=$("#regionList").val();
	var station=$("#stationList").val();
	var office=$("#branchList").val();
	var client=$("#client").val();
	var fromdate=$("#fromDate").val();
	var todate=$("#toDate").val();
	if(client=="Select")client="";
	fromdate=getFormattedDate(fromdate);
	todate=getFormattedDate(todate);	
	if(isNull(region)|| isNull(station)||isNull(office)){
		alert('Select Mandatory Field');
		if(isNull(region)){
			$("#regionList").focus();
		}
		else if(isNull(station)){
			$("#stationList").focus();
		}
		else if(isNull(office)){
			$("#branchList").focus();
		}
	}
	else if(fromdate==null || fromdate.trim().length<=0){
		alert('Please Select the Date Range');
	}
	else if(todate==null || todate.trim().length<=0){
		alert('Please Select the Date Range');
	}
	else if(new Date(fromdate)>new Date(todate)){
		alert('Please Enter Valid Date Range');
	}
	else{
		if(fromdate==todate)todate=todate.concat(" 11:59 PM");
		var url = '/udaan-report/pages/reportviewer/BranchBookingDispatchViewer.jsp?'
			 + "&region="+region 
			 + "&station="+station 
			 + "&office="+office 
			 + "&client="+client
			 + "&fromdate="+fromdate
			 + "&todate="+todate;
		
	 	window.open(url, "_blank");
	}
	
}

/**
 * clearScreen
 * @param type
 */
function clearScreen() {
	if(confirm("Do you want to clear the filter details?")) {
		var url="./brBkngBranchDesph.do?submitName=getBrBkngBranchReport";
		globalFormSubmit(url,'consignmentReportForm');
	}
	
}

