var noHubs=false;
function getHubStationsList(id, clearFields) {
	getDomElementById("stationList").options.length = 1;	
	getDomElementById("office").options.length = 1;
		var region = $("#" + id).val();
		if(isNaN(region)){
			return ;
		}
		var url = './baSalesReport.do?submitName=getStations&region='	+ region;

		ajaxCallWithoutForm(url, populateHubStationList);

}
function populateHubStationList(data) {
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
function getStationsList(){
	
	var region = document.getElementById('region').value;
	$("#stationList" ).empty();
	var url = './originHubTallyReport.do?submitName=getStations&region='
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
	
	var station = document.getElementById('stationList').value;
	$("#officeList" ).empty();
	var url = './originHubTallyReport.do?submitName=getHubOffices&station='
		+ station;
	ajaxCallWithoutForm(url, getHubOfficeListData);

}

function getHubOfficeListData(data){
	if (!isNull(data)) {
   
			var content = document.getElementById('office');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			/*var allOption;
			allOption = document.createElement("option");
			allOption.value = "";
			allOption.appendChild(document.createTextNode("All"));
			content.appendChild(allOption);*/
			$.each(data, function(index, value) {
				if(this.officeName!=undefined){
					noHubs = false;
				var option;
				option = document.createElement("option");
				option.value = this.officeId;
				option.appendChild(document.createTextNode(this.officeName));
				content.appendChild(option);
				}
				else{
					noHubs = true;
				}
	});
   }
  }

function getCountOfHubs(){
if(noHubs){
	alert("No Hubs For Selected Station");
}	
	
}

function getClientList(){

	var office = document.getElementById('office').value;
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
			defOption.appendChild(document.createTextNode("Select"));
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
function getOrginhubTally() {
	var region=$("#regionList").val();
	var regionName = document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
	var station=$("#stationList").val();
	var stationName = document.getElementById('stationList').options[document.getElementById('stationList').selectedIndex].text;
	var office=$("#office").val();
	var officeName = document.getElementById('office').options[document.getElementById('office').selectedIndex].text;     			
	var todate=$("#todate").val();
	todatef=getFormattedDate(todate);
	if(region=="Select"|| region=="All")region="";
	if(station=="Select"|| station=="All")station="";
	if(office=="Select"|| office=="All")office="";
	if(isNull(region)|| isNull(station)||isNull(office)){
		alert('Select Mandatory Field ');
		if(isNull(region)){
			$("#regionList").focus();
		}
		else if(isNull(station)){
			$("#stationList").focus();
		}
		else if(isNull(office)){
			$("#office").focus();
		}
	}
	else if(todate==null || todate.trim().length<=0){
		alert('Please Select the Date');
		$("#todate").focus();
	}
	
	else if(new Date(todatef)>new Date()){
		alert("Date Can Not Be Future Date");
		$("#todate").focus();
	}
	else{
	/*if(fromdate==todate)todate=todate.concat(" 11:59 PM");*/
		var url = '/udaan-report/pages/reportviewer/OriginHubTallyViewer.jsp?' 
				+ "&regionName=" + regionName
				+ "&stationName=" + stationName
				+ "&office=" + office 
				+ "&officeName=" + officeName
				+ "&todate=" + todate;
		window.open(url, "_blank");
	}
}

/**
 * clearScreen
 * @param type
 */
function clearOriginHubScreen() {
	if(confirm("Do you want to clear the filter details?")) {
		var url="./originHubTallyReport.do?submitName=viewFormDetails";
		globalFormSubmit(url,'consignmentReportForm');
	}
	
}


