var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
var sationDropDownId;
var vendorDropDownId;

function getStationsList(origionId,sationId){
	sationDropDownId = sationId;
	$("#"+sationId+"" ).empty();//need to checked ok
	var region=$("#"+origionId+"" ).val();
	var url = './airColoading.do?submitName=getStations&region='
		+ region;
	ajaxCallWithoutForm(url, getStationList);
}

function getVendorList(origionId,vendorId){
	var screenCode = "air";
	if(isNull(document.airColoadingForm)){
		screenCode = "Rail";
	}
	vendorDropDownId = vendorId;
	$("#"+vendorId+"" ).empty();//need to checked ok
	var region=$("#"+origionId+"" ).val();
	var url = './airColoading.do?submitName=getVendors&region='
		+ region+'&serviceType='+screenCode;
	ajaxCallWithoutForm(url, getVendors);
}

function getVendors(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		   
			var content = document.getElementById(vendorDropDownId);
			content.innerHTML = "";
			var defOption = document.createElement("option");
			defOption.value = "";
			defOption.appendChild(document.createTextNode("--Select--"));
			content.appendChild(defOption);
			$.each(data, function(index, value) {
				var option;
				option = document.createElement("option");
				option.value = this.vendorId;
				option.appendChild(document.createTextNode(this.vendorCode+'-'+this.businessName));
				content.appendChild(option);
	});
	getStationsList('origionRegionList','origionStationList');
   }
  }

function getStationList(data){
	if (!isNull(data)) {
		var responseText =data; 
		var error = responseText[ERROR_FLAG];
		//var success = responseText[SUCCESS_FLAG];
		if(responseText!=null && error!=null){
			alert(error);
			return ;
		}
		   
			var content = document.getElementById(sationDropDownId);
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


function checkMandatory(screen){
	var originRegionID = $("#origionRegionList" );
	if(originRegionID.val()=="" || originRegionID.val()=='-1'){
		alert('Select Mandatory Field Origin RHO');
		originRegionID.focus();
		return;
	}
	var originCityID = $("#origionStationList" );
	if(originCityID.val()=="" || originCityID.val()=='select'){
		alert('Select Mandatory Field Origin Station');
		originCityID.focus();
		return;
	}
	var destRegionID = $("#destinationRegionList" );
	if(destRegionID.val()=="" || destRegionID.val()=='-1'){
		alert('Select Mandatory Field Destination RHO');
		destRegionID.focus();
		return;
	}
	var destCityID = $("#destinationStationList" );
	if(destCityID.val()=="" || destCityID.val()=='select'){
		alert('Select Mandatory Field Destination Station');
		destCityID.focus();
		return;
	}
	if((originRegionID.val() == destRegionID.val()) && originCityID.val() == destCityID.val()){
		alert('Origin Station and Destination Station can not be same.');
		destCityID.focus();
		return;
	}
	var vendor = $("#vendorList" );
	if(vendor.val()=="" || vendor.val()=='select' || vendor.val() == undefined ){
		alert('Select Mandatory Field Vendor');
		vendor.focus();
		return;
	}
	var effectiveFrom = $("#effectiveFrom" );
	if(effectiveFrom.val()=="" || effectiveFrom.val()=='select'){
		alert('Select Mandatory Field Effective From');
		effectiveFrom.focus();
		return;
	}
	if(!checkDate('effectiveFrom')){
		return;
	}
	if("Air" == screen){
		var cdTypeList = $("#cdTypeList" );
		if(cdTypeList.val()=="" || cdTypeList.val()=='select'){
			alert('Select Mandatory Field CD Type');
			cdTypeList.focus();
			return;
		}
		var effectiveFrom = $("#effectiveFrom" );
		if(effectiveFrom.val()=="" || effectiveFrom.val()=='select'){
			alert('Select Mandatory Field Effective From');
			effectiveFrom.focus();
			return;
		}
		if(cdTypeList.val()=='AWB' && !checkMandatoryGridAirAWB()){
			return;
		}
		if(cdTypeList.val()=='CD' && !checkMandatoryGridAirCD()){
			return;
		}
		document.airColoadingForm.action = "/udaan-report/airColoading.do?submitName=saveAirData";
		document.airColoadingForm.submit();
	}else{
		if(!checkMandatoryGrid()){
			return;
		}
		document.trainColoadingForm.action = "/udaan-report/trainColoading.do?submitName=saveTrainData";
		document.trainColoadingForm.submit();
	}
}

function checkDate(dataId){
	var startDate=$("#"+dataId+"").val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1]-1, arrStartDate[0]);
	var date2 = new Date();
	if(date1=="" || date1<date2){
		alert("Please ensure that Effective date is greater than current date");
		$("#"+dataId+"" ).val("");
		$("#"+dataId+"" ).focus();
		return false;
		}
	else {
		return true;
	}


}

function clearScreen(action){
	if(confirm("Do you want to clear the page?")){
	var url = "./airColoading.do?submitName=preparePage";
		document.airColoadingForm.action=url;
		document.airColoadingForm.submit();
	}
}

function uploadTemplate() {
	var cdType = $("#cdTypeList");
	if(isNull(cdType.val())){
		alert('Select Mandatory Field CD Type');
		cdType.focus();
		return;
	}
	var fileValue = document.getElementById('templateFile').value;
	if (isNull(fileValue)) {
		alert('Please choose a file to upload');
		return false;
	}
	// validates the extension of the excel file
	else {
		var ext = fileValue.split(".");
		if (!(ext[1] == 'xls' || ext[1] == 'xlsx')) {
			alert('Files with only xls and xlsx format are allowed');
			return false;
		}

	}
	document.airColoadingForm.action = "/udaan-report/airColoading.do?submitName=uploadXlsFile";
	document.airColoadingForm.submit();

}

function checkMandatoryGridAirAWB(){
	var rowCount = $('#airColoadingTableAwb tr').length -1;
	for ( var i = 1; i <= rowCount; i++) {
		if (!validateSelectRowAIR(i)) {
			return false;
	}
}
return true;
}

function checkMandatoryGridAirCD(){
	var rowCount = $('#airColoadingTableCd tr').length -1;
	for ( var i = 1; i <= rowCount; i++) {
		if (!validateSelectRowCD(i)) {
			return false;
	}
}
return true;
}

function checkMandatoryGrid(){
	var rowCount = $('#trainDataTable tr').length -1;
	for ( var i = 1; i <= rowCount; i++) {
		if (!validateSelectRow(i)) {
			return false;
	}
}
return true;
}

/**
 * To validate AWB Excel data
 * 
 * @param rowId
 */
function validateSelectRow(rowId) {
	var varObj;
	varObj = getDomElementById("minChargeableRate" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Min Chargeable Rate." + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("ratePerKG" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Rate Per KG." + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("otherChargesPerKG" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Other Charges Per KG." + atLine);
		varObj.focus();
		return false;
	}
}
/**
 * To validate AWB Excel data
 * 
 * @param rowId
 */
function validateSelectRowAIR(rowId) {
	var varObj;
	varObj = getDomElementById("airLine" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(varObj.value)) {
		alert("Please provide AirLine Name." + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("flightNo" + rowId);
	if (isNull(varObj.value)) {
		alert("Please provide Flight No." + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("flightType" + rowId);
	if (isNull(varObj.value)) {
		alert("Please provide Flight Type." + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("minTariff" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Min Tariff" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("w1" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w1" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("w2" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w2" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("w3" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w3" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("w4" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w4" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("w5" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w5" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("w6" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w6" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("fuelSurcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Fuel Surcharge" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("octroiPerBag" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per Bag" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("octroiPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per KG" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("originTSPFlatRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin TSP Flat Rate" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("originTSPPerKGRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin TSP Per KG Rate" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("destinationTSPFlatRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination TSP Flat Rate" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("destinationTSPPerKGRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination TSP Per KG Rate" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("airportHandlingCharges" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Airport Handling Charges" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("xRayCharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct X-Ray Charge" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("originMinUtilizationCharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin Min Utilization Charge" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("originUtilizationChargesPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin Utilization Charges Per KG" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("destinationMinUtilizationCharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination Min Utilization Charge" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("destinationUtilizationChargesPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination Utilization Charges Per KG" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("serviceChargeOfAirline" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Service Charge Of Airline" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("surcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Surcharge" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("airWayBill" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Air Way Bill" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("discountedPercent" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Discounted Percent" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("sSPRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct SSP Rate" + atLine);
		varObj.focus();
		return false;
	}
	
	return true;
}

/**
 * To validate CD Excel data
 * 
 * @param rowId
 */
function validateSelectRowCD(rowId) {
	var varObj;
	varObj = getDomElementById("airLine" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(varObj.value)) {
		alert("Please provide AirLine Name." + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("flightNo" + rowId);
	if (isNull(varObj.value)) {
		alert("Please provide Flight No." + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("billingRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Billing Rate" + atLine);
		varObj.focus();
		return false;
	}
	
	varObj = getDomElementById("fuelSurcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Fuel Surcharge" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("octroiPerBag" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per Bag" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("octroiPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per KG" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("surcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Surcharge" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("otherCharges" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Other Charges" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("cd" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct CD" + atLine);
		varObj.focus();
		return false;
	}
	varObj = getDomElementById("sSPRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct SSP Rate" + atLine);
		varObj.focus();
		return false;
	}
	
	return true;
}

function loadTrainDetails(obj) {
	if(document.trainColoadingForm.action != undefined){
	document.trainColoadingForm.action = "/udaan-report/trainColoading.do?submitName=loadTrainDetails";
	document.trainColoadingForm.submit();
}
}