/**
 * isValidGatepassNo
 *
 * @param gatepassNoObj
 * @returns {Boolean}
 * @author narmdr
 */
function isValidGatepassNo() {
	var gatepassNo = $("#mblGatepassNo").val();
	var numpattern = /^[0-9]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	if (isNull(gatepassNo)){
		return true;
	}
	else if (gatepassNo.length!=12) {
		alert("Gatepass No. length must be of 12 characters!");
		$("#mblGatepassNo").focus();
	}
	else if(gatepassNo.substring(0, 1)!="G"){
		alert("Gatepass No. must Starts with G!");
		$("#mblGatepassNo").focus();
	}
	else if (!gatepassNo.substring(1, 5).match(alphaNumeric)){
		alert("Gatepass No. Format is not correct!");
		$("#mblGatepassNo").focus();
	}
	else if (!numpattern.test(gatepassNo.substring(5))){
		alert("Gatepass No. last 7 characters must be digit!");
		$("#mblGatepassNo").focus();
	}
	else return true;
}
function getOrgStationsList(){
	
	var region = document.getElementById('originRegion').value;
	$("#stationList" ).empty();
	var url = './mblGatePassReport.do?submitName=getStations&region='
		+ region;
	ajaxCallWithoutForm(url, getOrgStationList);
	
	
}

function getOrgStationList(data){
	if (!isNull(data)) {
			var content = document.getElementById('originStation');
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
function getDestStationsList(){
	var region = document.getElementById('destinationRegion').value;
	if(region=="Select"||region=="")region="";
	else if(region=="All")region="0";
	$("#destinationStation" ).empty();
	var url = './mblGatePassReport.do?submitName=getStations&region='
		+ region;
	ajaxCallWithoutForm(url, getDestStationList);

}

function getDestStationList(data){
	if (!isNull(data)) {
			var content = document.getElementById('destinationStation');
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("Select"));
			content.appendChild(defOption);
			var allOption;
			allOption = document.createElement("option");
			allOption.value = "";
			allOption.appendChild(document.createTextNode("All"));
			content.appendChild(allOption);
			$.each(data, function(index, value) {
				if(this.cityName!=undefined){
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
				}
			});
			
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
function getMblGatePassDetails() {
	var dispatchDate=$("#dispatchDate" ).val();
	/*var mblGatepassNo=$("#mblGatepassNo" ).val();*/
	var reportType=$("#reportType" ).val();
	var originRegion=$("#regionList" ).val();
	var originStation=$("#stationList" ).val();
	var destinationRegion=$("#destinationRegion" ).val();
	var destinationStation=$("#destinationStation" ).val();
	
	
	
	/*var mode=$("#mode" ).val();
	var loadForwardedBy=$("#loadForwardedBy" ).val();
	var flightTrainNo=$("#flightTrainNo" ).val().toUpperCase();
	var cdAwbRr=$("#cdAwbRr" ).val();
	var std=$("#std" ).val();
	var sta=$("#sta" ).val().toUpperCase() ;
	var vehicleNo=$("#vehicleNo" ).val();
	var driverName=$("#driverName" ).val();
	var departueTime=$("#departueTime" ).val();
	var arrivalTime=$("#arrivalTime" ).val();*/
	/*if(departueTime==null || departueTime=="")departueTime="00:00";
	if(arrivalTime==null || arrivalTime=="" ) arrivalTime="23:59";*/
	
	if(new Date(getFormattedDate(dispatchDate))>new Date()){
		alert("Date Can Not Be Future Date");
	}
	else if(originRegion=="Select"){
		alert('Please Select Origin Region ');
			$("#regionList").focus();
		}
	else if(isNull(destinationRegion)){
			alert('Please Select Destination Region ');
			$("#destinationRegion").focus();
		}
	
	else{
		
		if(originRegion=="Select"|| originRegion=="All")originRegion="";
		if(originStation=="Select"|| originStation=="All")originStation="";
		if(destinationRegion=="Select"|| destinationRegion=="All")destinationRegion="";
		if(destinationStation=="Select"|| destinationStation=="All")destinationStation="";
		
		var reportTypeName = document.getElementById('reportType').options[document.getElementById('reportType').selectedIndex].text;
		var originRegionName = document.getElementById('regionList').options[document.getElementById('regionList').selectedIndex].text;
		var destinationRegionName = document.getElementById('destinationRegion').options[document.getElementById('destinationRegion').selectedIndex].text;
		var originStationName = document.getElementById('stationList').options[document.getElementById('stationList').selectedIndex].text;
		var destinationStationName = document.getElementById('destinationStation').options[document.getElementById('destinationStation').selectedIndex].text;
		//alert(destinationStationName +"-" +originStationName );
		
		
		var url = '/udaan-report/pages/reportviewer/MblGatePassViewer.jsp?'
			 + "&dispatchDate="+dispatchDate 
			 /*+ "&mblGatepassNo="+mblGatepassNo*/ 
			 + "&reportType="+reportType
			 + "&originRegion="+originRegion
			 + "&originStation="+originStation
			 + "&destinationRegion="+destinationRegion
			 + "&destinationStation="+destinationStation
			 + "&ReportTypeName="+reportTypeName
			 + "&OriginRegionName="+originRegionName
			 + "&DestinationRegionName="+destinationRegionName
		 	 + "&OriginStationName="+originStationName
		 	 + "&DestinationStationName="+destinationStationName;
			 
			/* + "&mode="+mode
			 + "&loadForwardedBy="+loadForwardedBy
			 + "&flightTrainNo="+flightTrainNo
			 + "&cdAwbRr="+cdAwbRr
			 + "&std="+std
			 + "&sta="+sta
			 + "&vehicleNo="+vehicleNo
			 + "&driverName="+driverName
			 + "&departueTime="+departueTime
			 + "&arrivalTime="+arrivalTime;*/
		
		window.open(url, "_blank");
	 }
	}

/**
 * clearScreen
 * @param type
 */
function clearFilterScreen() {	
	//var url="./mblGatePassReport.do?submitName=viewFormDetails";
	//submitMblForm(url,'Clear');	
	if(confirm("Do you want to clear the filter details?")) {
		document.forms[0].reset();		
		getDomElementById("stationList").options.length = 1;	
		getDomElementById("destinationStation").options.length = 1;
	}

}

function submitMblForm(url,action){	
	if(confirm("Do you want to "+action+" the details?")) {
		document.consignmentReportForm.action = url;
		document.consignmentReportForm.method = "post";
		document.consignmentReportForm.submit();
	}
}
function alphaNumericValidate(e) {	
	
	var specialKeys = new Array();
    specialKeys.push(8); //Backspace
    specialKeys.push(9); //Tab
    specialKeys.push(46); //Delete
    specialKeys.push(36); //Home
    specialKeys.push(35); //End
    specialKeys.push(37); //Left
    specialKeys.push(39); //Right
	var keyCode = e.keyCode == 0 ? e.charCode : e.keyCode;
    var ret = (
    		(keyCode >= 48 && keyCode <= 58) || 
    		(keyCode >= 65 && keyCode <= 90) || 
    		(keyCode >= 97 && keyCode <= 122) || 
    		(specialKeys.indexOf(e.keyCode) != -1 && e.charCode != e.keyCode)
    		);    
    return ret;
}

function onlyAlphabet(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox	
	
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 46 || (charCode >= 48 && charCode <= 57) 
			|| charCode == 32 || charCode == 9 || charCode == 8
			|| charCode == 127 || charCode == 37 || charCode == 39
			|| charCode == 0) {
		return true;
	} else {
		return false;
	}

}

function onlyNumeric(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	
	
	if ((charCode >= 48 && charCode <= 58) || charCode == 13 || charCode == 8 || charCode == 0) 
			{
		return true;
	} else {
		return false;
	}

}

/**
 * validateTime
 * @param time
 * @param msg
 * @returns {Boolean}
 */
function validateTime(time,msg){
	var hours,minutes;
	var inTime=time.split(":");
	if(isNull(inTime) || inTime.length<2){
		alert("Please provide "+msg+" time in HH:MM format");
		return false;
	}
	hours=inTime[0];
	minutes=inTime[1];
	if(hours.length !=2 || minutes.length !=2){
		alert("Please provide "+msg+" time in HH:MM format");
		return false;
	}
	if(isNaN(hours) || isNaN(minutes)){
		alert("Please provide  valid "+msg+" time ");
		return false;
	}
	/**validate  Hours */
	if (!(parseInt(hours, 10) >= 0 && parseInt(hours, 10) < 24)) {
		alert(" Hours should be in between 0-23");
		return false;
	}
	/**validate minutes*/
	if (!(parseInt(minutes, 10) >= 0 && parseInt(minutes, 10) < 60)) {
		alert("Minutes should be in between 0-59");
		return false;
	}
	return true;
	
}


function getMbplStationsList(id, clearFields) {
	clearAllFields();
	var region = $("#" + id).val();
	if (!isNull(region)) {
		if(region=="All")region="0";
		var url = './mblGatePassReport.do?submitName=getStations&region=' + region;

		ajaxCallWithoutForm(url, populateStationList);
	}
}

/*function getMbplStationsList(){
	var region = document.getElementById('destinationRegion').value;
	if(region=="Select"||region=="")region="";
	else if(region=="All")region="0";
	$("#destinationStation" ).empty();
	var url = './mblGatePassReport.do?submitName=getStations&region='
		+ region;
	//ajaxCallWithoutForm(url, getDestStationList);
	ajaxCallWithoutForm(url, populateStationList);

}*/