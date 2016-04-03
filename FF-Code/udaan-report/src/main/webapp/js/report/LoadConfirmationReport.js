function getDestnStationsList(id) {

	var region = $("#" + id).val();
	if (region == "Select" || region == "")
		region = "";
	else if (region == "All")
		region = "0";
	$("#destStationList").empty();
	var url = './loadConfirmation.do?submitName=getStations&region=' + region;
	ajaxCallWithoutForm(url, populateDestStationList);

}

function populateDestStationList(data) {
	if (!isNull(data)) {
		var content = document.getElementById('destStationList');
		content.innerHTML = "";
		var defOption;
		defOption = document.createElement("option");
		defOption.value = "Select";
		defOption.appendChild(document.createTextNode("Select"));
		content.appendChild(defOption);
		var allOption;
		allOption = document.createElement("option");
		allOption.value = "All";
		allOption.appendChild(document.createTextNode("All"));
		content.appendChild(allOption);
		$.each(data, function(index, value) {
			if (this.cityName != undefined) {
				var option;
				option = document.createElement("option");
				option.value = this.cityId;
				option.appendChild(document.createTextNode(this.cityName));
				content.appendChild(option);
			}
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
function getLoadConfirmationDetails() {
	var regionList = $("#regionList").val();
	var orgRegionName = document.getElementById('regionList').options[document
		    	                                               			.getElementById('regionList').selectedIndex].text;
	var stationList = $("#stationList").val();
	var orgStationName = document.getElementById('stationList').options[document
		    	                                               			.getElementById('stationList').selectedIndex].text;
	var destRegionList = $("#destRegionList").val();
	var destRegionName = document.getElementById('destRegionList').options[document
		    	                                               			.getElementById('destRegionList').selectedIndex].text;
	var destStationList = $("#destStationList").val();
	var destStationName = document.getElementById('destStationList').options[document
		    	                                               			.getElementById('destStationList').selectedIndex].text;
	
	var servicedByType = $("#serviceByType").val();
	var transportMode = $("#transportMode").val();
	
	if (regionList == "Select" || regionList == "All")
		regionList = "";
	if (stationList == "Select" || stationList == "All")
		stationList = "Select";
	if (destRegionList == "Select" || destRegionList == "All")
		destRegionList = "";
	if (destStationList == "Select" || destStationList == "All")
		destStationList = "Select";
	
	var fromDate = $("#fromDate").val();
	var toDate = $("#toDate").val();
	fromDate = getFormattedDate(fromDate);
	toDate = getFormattedDate(toDate);
	
	if (isNull(regionList) || isNull(destRegionList)) {
		alert('Select Mandatory Field');
		if (isNull(regionList)) {
			$("#regionList").focus();
		} else if (isNull(destRegionList)) {
			$("#destRegionList").focus();
		}
	} else if (fromDate == null || fromDate.trim().length <= 0) {
		alert('Please Select the Date Range');
		$("#fromDate").focus();
	} else if (toDate == null || toDate.trim().length <= 0) {
		alert('Please Select the Date Range');
		$("#toDate").focus();
	} else if (new Date(fromDate) > new Date()) {
		alert("Date Can Not Be Future Date");
		$("#fromDate").focus();
	} else if (new Date(toDate) > new Date()) {
		alert("Date Can Not Be Future Date");
		$("#toDate").focus();
	} else if (new Date(fromDate) > new Date(toDate)) {
		alert('Please Enter Valid Date Range');
		$("#fromDate").focus();
	} else {
		if (fromDate == toDate)
			toDate = toDate.concat(" 11:59 PM");
		var url = "/udaan-report/pages/reportviewer/LoadConfirmationViewer.jsp?"
				+ "orgRegion="
				+ regionList
				+ "&destRegion="
				+ destRegionList
				+ "&orgStation="
				+ stationList
				+ "&destStation="
				+ destStationList
				+ "&fromDate="
				+ fromDate
				+ "&toDate="
				+ toDate
				+ "&orgRegionName="
				+ orgRegionName
				+ "&orgStationName="
				+ orgStationName
				+ "&destRegionName="
				+ destRegionName
				+ "&destStationName="
				+ destStationName
				+ "&servicedByType="
				+ servicedByType
				+ "&transportMode=" 
				+ transportMode;
		window.open(url, "_blank");
	}
}
/**
 * clearScreen
 * 
 * @param type
 */
function clearFilterScreen() {
	if (confirm("Do you want to clear the filter details?")) {
		var url = "./loadConfirmation.do?submitName=getloadConfirmationReport";
		globalFormSubmit(url, 'consignmentReportForm');
	}
}

/**
 * getServiceByTypeListByMode
 * 
 * @author narmdr
 */
function getServiceByTypeListByMode() {
	var transportModeId = getDomElementById("transportMode").value;
	if (isNull(transportModeId)) {
		clearDropDownList("serviceByType");
		return;
	}
	var url = "./loadConfirmation.do?submitName=getServiceByTypeListByTransportModeId&transportModeId="
			+ transportModeId;
	$.ajax({
		url : url,
		success : function(req) {
			populateServiceByType(req);
		}
	});
}
/**
 * populateServiceByType
 * 
 * @param req
 * @author narmdr
 */
function populateServiceByType(req) {
	var serviceByTypeList = eval('(' + req + ')');
	clearDropDownList("serviceByType");
	var errorMsg = getErrorMessage(serviceByTypeList);
	if (!isNull(errorMsg)) {
		alert(errorMsg);
		return;
	}
	for ( var i = 0; i < serviceByTypeList.length; i++) {
		addOptionTODropDown("serviceByType", serviceByTypeList[i].label,
				serviceByTypeList[i].value);
	}
}

/* @Desc:For Clearing the dropdown */
function clearDropDownList(selectId) {
	document.getElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, "ALL", "");

}

/**
 * addOptionTODropDown
 *
 * @param selectId
 * @param label
 * @param value
 * @author narmdr
 */
function addOptionTODropDown(selectId, label, value){
	var myselect=document.getElementById(selectId);
	try{
		 myselect.add(new Option(label, value), null); //add new option to end of "myselect"
	}
	catch(e){ //in IE, try the below version instead of add()
		 myselect.add(new Option(label, value)); //add new option to end of "myselect"
	}
}