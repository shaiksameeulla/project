function stopRKey(evt) { 
  var evt = (evt) ? evt : ((event) ? event : null); 
  var node = (evt.target) ? evt.target : ((evt.srcElement) ? evt.srcElement : null); 
  if ((evt.keyCode == 13) && (node.type=="text"))  {return false;} 
} 

document.onkeypress = stopRKey;

var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";
var sationDropDownId;
var vendorDropDownId;

var Fixed_Trip = "FT";
var Fixed_Day_KM = "FD";
var Fixed_Month_KM = "FM";
var PerDay_Fuel = "PD+F";
var PerMonth_Fuel = "PM+F";

var clearMessage = "Do you want to clear the page?";

function getStationsList(origionId, sationId) {
	eraseEffectivefromDate();
	
	sationDropDownId = sationId;
	$("#" + sationId + "").empty();	// need to checked ok
	var region = $("#" + origionId + "").val();
	var url = './airColoading.do?submitName=getStations&region=' + region;
	ajaxCallWithoutForm(url, getStationList);
}

function getVendorList(origionId, vendorId) {
	var screenCode = "air";
	if (isNull(document.airColoadingForm)) {
		screenCode = "Rail";
	}
	
	vendorDropDownId = vendorId;
	$("#" + vendorId + "").empty();// need to checked ok
	var region = $("#" + origionId + "").val();
	var url = './airColoading.do?submitName=getVendors&region=' + region
			+ '&serviceType=' + screenCode;
	ajaxCallWithoutForm(url, getVendors);
}

function getDutyHours(obj) {
	var date = document.getElementById("date");
	if (date.value == "") {
		alert('Please select date');
		date.focus();
		obj.value = "";
		return false;
	}
	
	if (!checkPreviusDate('date')) {
		return false;
	}
	
	var url = './vehicleServiceEntry.do?submitName=getDutyHours&date=' + date.value
			+ '&vehicleNumber=' + obj.value;
	ajaxCallWithoutForm(url, setDutyHours);
}

function setDutyHours(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var dutyHours = document.getElementById("dutyHours");
		$.each(data, function(index, value) {
			if(this.dutyHours == '0'){
				dutyHours.value = '';
			}else{
				dutyHours.value = this.dutyHours;
			}
		});
		
	}
}

function getVendors(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}

/*		var content = document.getElementById(vendorDropDownId);
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.vendorId;
			option.appendChild(document.createTextNode(this.vendorCode + '-'
					+ this.businessName));
			content.appendChild(option);
		});*/
		
		getStationsList('origionRegionList', 'origionStationList');
	}
}

function getStationList(data) {
	if (!isNull(data)) {
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		// var success = responseText[SUCCESS_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
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

function checkMandatory(screen) {
	var originRegionID = $("#origionRegionList");
	if (originRegionID.val() == "" || originRegionID.val() == '-1') {
		alert('Select Mandatory Field Origin RHO');
		originRegionID.focus();
		return false;
	}
	var originCityID = $("#origionStationList");
	if (originCityID.val() == "" || originCityID.val() == 'select') {
		alert('Select Mandatory Field Origin Station');
		originCityID.focus();
		return false;
	}
	var destRegionID = $("#destinationRegionList");
	if (destRegionID.val() == "" || destRegionID.val() == '-1') {
		alert('Select Mandatory Field Destination RHO');
		destRegionID.focus();
		return false;
	}
	var destCityID = $("#destinationStationList");
	if (destCityID.val() == "" || destCityID.val() == 'select') {
		alert('Select Mandatory Field Destination Station');
		destCityID.focus();
		return false;
	}
	if ((originRegionID.val() == destRegionID.val())
			&& originCityID.val() == destCityID.val()) {
		alert('Origin Station and Destination Station cannot be same.');
		destCityID.focus();
		return false;
	}
	var vendor = $("#vendorList");
//	alert("vendor value : " + vendor.val());
	if (vendor.val() == "" || vendor.val() == 'select'
			|| vendor.val() == undefined) {
		alert('Select Mandatory Field Vendor');
		vendor.focus();
		return false;
	}
	var effectiveFrom = $("#effectiveFrom");
	if (effectiveFrom.val() == "" || effectiveFrom.val() == 'select') {
		alert('Select Mandatory Field Effective From');
		effectiveFrom.focus();
		return false;
	}
	if ("Air" == screen) {
		var cdTypeList = $("#cdTypeList");
		if (cdTypeList.val() == "" || cdTypeList.val() == 'select') {
			alert('Select Mandatory Field CD Type');
			cdTypeList.focus();
			return false;
		}
		if (cdTypeList.val() == 'AWB' && !checkMandatoryGridAirAWB()) {
			return false;
		}
		if (cdTypeList.val() == 'CD' && !checkMandatoryGridAirCD()) {
			return false;
		}
	} else if ("Train" == screen) {
		if (!checkMandatoryGrid()) {
			return false;
		}
	}

	return true;
}

function checkVehicleMandatory() {
	var obj = document.getElementById("vehicleNo");
	if (isNull(obj.value)) {
		alert("Please provide vehicle number");
		obj.focus();
		return false;
	}
	var rateObj = document.getElementById("rateType");
	if (rateObj.value != Fixed_Trip) {
		obj = document.getElementById("dutyHours");
		if (isNull(obj.value)) {
			alert("Please select duty Hours");
			obj.focus();
			return false;
		} else {
			if (obj.value != '24')
				obj = document.getElementById("perHourRate");
			if (isNull(obj.value) || isNaN(obj.value)) {
				alert("Please provide non-zero per hour OT Rate");
				obj.focus();
				return false;
			}
		}
	}

	obj = document.getElementById("vendorList");
	if (isNull(obj.value)) {
		alert("Please select vendor name");
		obj.focus();
		return false;
	}
	
	obj = document.getElementById("effectiveFrom");
	if (isNull(obj.value)) {
		alert("Please provide effective from date");
		obj.focus();
		return false;
	}

	if (!checkDate('effectiveFrom')) {
		return false;
	}

	var rateObj = document.getElementById("rateType");
	if (isNull(rateObj.value)) {
		alert("Please select rate type");
		rateObj.focus();
		return false;
	} else {
		obj = document.getElementById("rent");
		if (isNaN(obj.value / 0) || isNull(obj.value) || isNaN(obj.value)) {
			alert("Please provide non-zero rent");
			obj.focus();
			return false;
		}
		if (rateObj.value == PerDay_Fuel || rateObj.value == PerMonth_Fuel) {

			obj = document.getElementById("average");
			if (isNull(obj.value) || isNaN(obj.value) || checkTwoOrMoreDots(obj.value)) {
				alert("Please provide non-zero average");
				obj.focus();
				return false;
			}
			
/*			if(obj == "") {
				alert("Please provide average value.");
				obj.focus();
				return false;
			}
			
			var vehicleFreeKm = document.getElementById("freeKm");
			var vehiclePerKmRate = document.getElementById("perKmRate");
			if(vehicleFreeKm != null) {
				if(vehiclePerKmRate == null || vehiclePerKmRate == ""){
					alert("Please provide Per KM (Rate) value.");
					return false;
				}
			}*/
			
			/*obj = document.getElementById("freeKm");
			if (isNull(obj.value) || isNaN(obj.value)) {
				alert("Please provide non-zero free Km");
				obj.focus();
				return false;
			}
			if (!isNull(obj.value)) {
				obj = document.getElementById("perKmRate");
				if (isNull(obj.value) || isNaN(obj.value)) {
					alert("Please provide non-zero per Km Rate");
					obj.focus();
					return false;
				}
			}*/

		}
		
		var rateTypeObj = document.getElementById("rateType");
		var vehicleFreeKm = document.getElementById("freeKm");
//		alert("rateTypeObj: " + rateTypeObj);
		if (rateTypeObj.value == Fixed_Day_KM || rateTypeObj.value == Fixed_Month_KM) {
//			var vehicleFreeKm = document.getElementById("freeKm");
			if(vehicleFreeKm.value == null || vehicleFreeKm.value == ""){
				alert("Please provide Free KM value.");
				return false;
			}
		}
		
		var vehiclePerKmRate = document.getElementById("perKmRate");
		if(vehicleFreeKm.value != null || vehicleFreeKm.value != ""){
			if(vehiclePerKmRate.value == null || vehiclePerKmRate.value == ""){
				alert("Please provide per KM value.");
				return false;
			}
		}
		
		if (rateObj.value != Fixed_Trip && rateObj.value != Fixed_Day_KM && rateObj.value != Fixed_Month_KM && rateObj.value != PerDay_Fuel) {
			obj = document.getElementById("noOfDays");
			if (isNull(obj.value) || isNaN(obj.value)) {
				alert("Please provide non-zero no of Days");
				obj.focus();
				return false;
			}
		}
	}
	
	obj = document.getElementById("fuelType");
	if (isNull(obj.value)) {
		alert("Please select fuel type");
		obj.focus();
		return false;
	}
	
	obj = document.getElementById("rent");
	obj.value = roundToOne(obj.value);
	obj = document.getElementById("average");
	obj.value = roundToOne(obj.value);
	return true;
}

function makeMandatory(obj) {
	var rateMandate = document.getElementById("rateMandate");
	if (obj.value != '24') {
		rateMandate.style.display = 'block';
	} else {
		rateMandate.style.display = 'none';
	}
}
function checkVehicleServiceEntryMandatory() {

	
	obj = document.getElementById("date");
	if (isNull(obj.value)) {
		alert("Please provide date");
		obj.focus();
		return false;
	}

	if (!checkPreviusDate('date')) {
		return false;
	}

	var rateObj = document.getElementById("vehicalNumber");
	if (isNull(rateObj.value)) {
		alert("Please select vehical Number");
		rateObj.focus();
		return false;
	}

/*	obj = document.getElementById("dutyHours");
	if (isNull(obj.value)) {
		alert("Contract does not exist for selected vehicle.");
		obj.focus();
		return false;
	}*/
	
	
	obj = document.getElementById("ot");
	if (obj.value != '' && obj.value == 0 ) {
		alert("OT can not be Zero");
		obj.focus();
		return false;
	}
	if (obj.value >= 24 ) {
		alert("OT can not be greater than or equale to 24");
		obj.focus();
		return false;
	}
	
	var ok = document.getElementById("openingKm");
	if (isNull(ok.value) || isNaN(ok.value)) {
		alert("Please provide non-zero opening Km");
		ok.focus();
		return false;
	}
	if (ok.value == 0 ) {
		alert("Opening Km can not be Zero");
		ok.focus();
		return false;
	}
	
	var ck = document.getElementById("closingKm");
	if (isNull(ck.value) || isNaN(ck.value)) {
		alert("Please provide non-zero closing Km");
		ck.focus();
		return false;
	}
	
	if (ck.value === 0 ) {
		alert("Closing Km can not be Zero");
		ck.focus();
		return false;
	}
	var openKM = ok.value;
	var closeKM = ck.value;
	if (parseInt(openKM) > parseInt(closeKM)) {
		alert("Opening Km cannot greater then closing Km");
		ok.focus();
		return false;
	}
	return true;
}

function checkSurfaceRateEntryMandatory() {

	obj = document.getElementById("fromDate");
	if (isNull(obj.value)) {
		alert("Please provide fromDate");
		obj.focus();
		return false;
	}

	/*if (!checkPreviusDate('fromDate')) {
		return false;
	}*/

	obj = document.getElementById("vendorId");
	if (isNull(obj.value)) {
		alert("Please select vendor name");
		obj.focus();
		return false;
	}

	obj = document.getElementById("toWeight");
	if (isNull(obj.value) || isNaN(obj.value) || obj.value == 0) {
		alert("Please provide non-zero to weight");
		obj.focus();
		return false;
	}
	
	obj = document.getElementById("rate");

/*	if (obj.value == 0.0) {
		alert("Rate cannot be "+ obj.value);
		obj.focus();
		return false;
	}*/
	if (checkTwoOrMoreDots(obj.value)) {
		alert("Please provide valid rate");
		obj.focus();
		return false;
	}
	if (isNull(obj.value) || isNaN(obj.value) || obj.value == 0.0) {
		alert("Please provide non-zero rate");
		obj.focus();
		return false;
	} else {
		obj.value = roundToTwo(obj.value);
	}
	
	obj = document.getElementById("additionalPerKg");
	/*	if (obj.value == 0.0) {
		alert("Additional Per Kg rate cannot be "+obj.value);
		obj.focus();
		return false;
	}*/
	
	if (checkTwoOrMoreDots(obj.value)) {
		alert("Please provide valid additional per Kg rate");
		obj.focus();
		return false;
	}
	
	if (isNull(obj.value) || isNaN(obj.value) || obj.value == 0.0) {
		alert("Please provide non-zero additional per Kg rate");
		obj.focus();
		return false;
	} else {
		obj.value = roundToTwo(obj.value);
	}

	return true;
}

function checkFuelMandatory() {
	var decimalsAllowed = 2;
	
	var obj = document.getElementById("effectiveFrom");
	if (isNull(obj.value)) {
		alert("Please provide effective from date");
		obj.focus();
		return false;
	}

	if (!checkDate('effectiveFrom')) {
		return false;
	}

	obj = document.getElementById("cityId");
	if (isNull(obj.value)) {
		alert("Please select city");
		obj.focus();
		return false;
	}
	
	obj = document.getElementById("petrol");
	dectext = checkDecimals(obj.value);
	
	if (dectext.length > decimalsAllowed){
		alert ("Enter Petrol rate with up to " + decimalsAllowed + " decimal places and try again.");
		obj.focus();
		return false;
	}
	
	if (isNull(obj.value) || obj.value == 0) {
		alert("Please provide non-zero petrol rate");
		obj.focus();
		return false;
	} else if (checkTwoOrMoreDots(obj.value)) {
		alert("Please enter the valid data");
		obj.focus();
		return false;
	}else if (obj.value > 999.99) {
		alert("Price cannot be greater than 999.99");
		obj.focus();
		return false;
	}else {
		obj.value = roundToTwo(obj.value);
	}
	
	obj = document.getElementById("diesel");
	dectext = checkDecimals(obj.value);
	
	if (dectext.length > decimalsAllowed){
		alert ("Enter Diesel rate with up to " + decimalsAllowed + " decimal places and try again.");
		obj.focus();
		return false;
	}
	
	if (isNull(obj.value) || obj.value == 0) {
		alert("Please provide non-zero diesel rate");
		obj.focus();
		return false;
	} else if (checkTwoOrMoreDots(obj.value)) {
		alert("Please enter the valid data");
		obj.focus();
		return false;
	} else if (obj.value > 999.99) {
		alert("Price cannot be greater than 999.99");
		obj.focus();
		return false;
	}else {
		obj.value = roundToTwo(obj.value);
	}
	
	obj = document.getElementById("cng");
	dectext = checkDecimals(obj.value);
	
	if (dectext.length > decimalsAllowed){
		alert ("Enter CNG rate with up to " + decimalsAllowed + " decimal places and try again.");
		obj.focus();
		return false;
	}
	
	if (isNull(obj.value) || obj.value == 0) {
		alert("Please provide non-zero CNG rate");
		obj.focus();
		return false;
	} else if (checkTwoOrMoreDots(obj.value)) {
		alert("Please enter the valid data");
		obj.focus();
		return false;
	}else if (obj.value > 999.99) {
		alert("Price cannot be greater than 999.99");
		obj.focus();
		return false;
	}else {
		obj.value = roundToTwo(obj.value);
	}
	
	obj = document.getElementById("lpg");
	dectext = checkDecimals(obj.value);
	
	if (dectext.length > decimalsAllowed){
		alert ("Enter LPG rate with up to " + decimalsAllowed + " decimal places and try again.");
		obj.focus();
		return false;
	}
	
	if (isNull(obj.value) || obj.value == 0) {
		alert("Please provide non-zero LPG rate");
		obj.focus();
		return false;
	} else if (checkTwoOrMoreDots(obj.value)) {
		alert("Please enter the valid data");
		obj.focus();
		return false;
	}else if (obj.value > 999.99) {
		alert("Price cannot be grater than 999.99");
		obj.focus();
		return false;
	}else {
		obj.value = roundToTwo(obj.value);
	}
	return true;
}
function enabledField(obj, id) {
	if (obj.value == Fixed_Trip) {
		var dutyHours = document.getElementById("dutyHours");
		dutyHours.disabled = true;
		$('#dutyHours').prop('selectedIndex', 0);
		var perHourRate = document.getElementById("perHourRate");
		perHourRate.disabled = true;
		perHourRate.value = '';
		var average = document.getElementById("average");
		average.disabled = true;
		average.value = '';
		var freeKm = document.getElementById("freeKm");
		freeKm.disabled = true;
		freeKm.value = '';
		var perKmRate = document.getElementById("perKmRate");
		perKmRate.disabled = true;
		perKmRate.value = '';
		var noOfDays = document.getElementById("noOfDays");
		noOfDays.disabled = true;
		noOfDays.value = '';
	} else {
		var dutyHours = document.getElementById("dutyHours");
		dutyHours.disabled = false;
		var perHourRate = document.getElementById("perHourRate");
		perHourRate.disabled = false;
		var average = document.getElementById("average");
		average.disabled = false;
		var freeKm = document.getElementById("freeKm");
		freeKm.disabled = false;
		var perKmRate = document.getElementById("perKmRate");
		perKmRate.disabled = false;
		var noOfDays = document.getElementById("noOfDays");
		noOfDays.disabled = false;
		if (obj.value == Fixed_Day_KM || obj.value == Fixed_Month_KM) {
			var average = document.getElementById("average");
			average.disabled = true;
			average.value = '';
			var noOfDays = document.getElementById("noOfDays");
			noOfDays.disabled = true;
			noOfDays.value = '';
		} 
		if (obj.value == PerDay_Fuel || obj.value == PerMonth_Fuel) {
			var freeKm = document.getElementById("freeKm");
			freeKm.disabled = true;
			freeKm.value = '';
			var perKmRate = document.getElementById("perKmRate");
			perKmRate.disabled = true;
			perKmRate.value = '';
			if (obj.value == PerDay_Fuel){
			var noOfDays = document.getElementById("noOfDays");
			noOfDays.disabled = true;
			noOfDays.value = '';
			}
		} 
		
	}
}

function saveData(screen) {
	if (checkMandatory(screen)) {
		var efDate = getValueByElementId("effectiveFrom");
		var vendorId = getValueByElementId("vendorList");
		
		var vendorList = [];

		var venList = $("#vendorList > option");
		venList.each(function() {
			vendorList.push(this.text + '=' + this.value);			
		});
		
		var vendorName = $("#vendorList option:selected").text();
		
		document.trainColoadingForm.action = "/udaan-config-admin/trainColoading.do?submitName=saveTrainData&efDate=" + efDate + "&vendorName=" + vendorName + 
	"&vendorId=" + vendorId + "&vendorList=" + vendorList;
		document.trainColoadingForm.submit();
	}
}

function renew(screen) {
	var efDate = getValueByElementId("effectiveFrom");
	var vendorId = getValueByElementId("vendorList");
//	var vendorName = $("#vendorList option:selected").text();
	var vendorList = [];

	var venList = $("#vendorList > option");
	venList.each(function() {
		vendorList.push(this.text + '=' + this.value);
	});
	
	var vendorName = $("#vendorList option:selected").text();
	
	if ("Air" == screen) {
		document.airColoadingForm.action = "/udaan-config-admin/airColoading.do?submitName=renewAir&efDate=" + efDate + "&vendorName=" + vendorName + "&vendorId=" + vendorId;
		document.airColoadingForm.submit();
	} else {
		document.trainColoadingForm.action = "/udaan-config-admin/trainColoading.do?submitName=renewTrain&efDate=" + efDate + "&vendorName=" + vendorName + 
	"&vendorId=" + vendorId + "&vendorList=" + vendorList;
		
		document.trainColoadingForm.submit();
	}
}

function submitForm(screen) {
	if (checkMandatory(screen)) {
		
		var efDate = getValueByElementId("effectiveFrom");
		var vendorId = getValueByElementId("vendorList");
		var vendorName = $("#vendorList option:selected").text();
		
		if ("Air" == screen) {
			document.airColoadingForm.action = "/udaan-config-admin/airColoading.do?submitName=submitAirData&efDate=" + efDate + "&vendorName=" + vendorName + "&vendorId=" + vendorId;
			document.airColoadingForm.submit();
		} else {
			var vendorList = [];

			var venList = $("#vendorList > option");
			venList.each(function() {
				vendorList.push(this.text + '=' + this.value);				
			});
			
			document.trainColoadingForm.action = "/udaan-config-admin/trainColoading.do?submitName=submutTrainData&efDate=" + efDate + "&vendorName=" + vendorName + "&vendorId=" + vendorId + "&vendorList=" + vendorList;;
			document.trainColoadingForm.submit();
		}
	}
}

function submitVehicleForm() {
	if (checkVehicleMandatory()) {
		document.vehiclesContractColoadingForm.action = "./vehiclesContractColoading.do?submitName=submitAction";
		document.vehiclesContractColoadingForm.submit();
	}
}

function submitFuelRateEntry() {
	if (checkFuelMandatory()) {
		document.fuelRateEntryForm.action = "./fuelRateEntryColoading.do?submitName=submitAction";
		document.fuelRateEntryForm.submit();
	}
}

function renewFuelRateEntry() {
	document.fuelRateEntryForm.action = "./fuelRateEntryColoading.do?submitName=renewAction";
	document.fuelRateEntryForm.submit();
}

function renewVehicle() {
	document.vehiclesContractColoadingForm.action = "./vehiclesContractColoading.do?submitName=renewAction";
	document.vehiclesContractColoadingForm.submit();
}

function submitSurfaceRateEntryForm() {
	if (checkSurfaceRateEntryMandatory()) {
		document.surfaceRateEntryForm.action = "./surfaceRateEntry.do?submitName=submitAction";
		document.surfaceRateEntryForm.submit();
	}
}

function submitVehicleServiceEntryForm() {
	if (checkVehicleServiceEntryMandatory()) {
		document.vehicleServiceEntryForm.action = "./vehicleServiceEntry.do?submitName=submitAction";
		document.vehicleServiceEntryForm.submit();
	}
}

function checkEffectiveFromDate(dataId, renewFlag) {
	if(renewFlag != "" && null != renewFlag) {
		if(renewFlag != 'R') {
			var cdTypeList = document.getElementById("cdTypeList");
			cdTypeList.selectedIndex = 0;
		}
	}
	
	var startDate = $("#" + dataId + "").val();
	
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var date2 = new Date();
	
	if (date1 == "" || date1 < date2) {
		alert("Please ensure that Effective date is greater than current date");
		$("#" + dataId + "").val("");
		$("#" + dataId + "").focus();
		return false;
	} else {
		return true;
	}
}

function checkDate(dataId) {
	var startDate = $("#" + dataId + "").val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var date2 = new Date();
	if (date1 == "" || date1 < date2) {
		alert("Please ensure that Effective date is greater than current date");
		$("#" + dataId + "").val("");
		$("#" + dataId + "").focus();
		return false;
	} else {
		return true;
	}
}

function checkPreviusDate(dataId) {
	var startDate = $("#" + dataId + "").val();
	var arrStartDate = startDate.split("/");
	var date1 = new Date(arrStartDate[2], arrStartDate[1] - 1, arrStartDate[0]);
	var date2 = new Date();
	if (date1 == "" || date1 > date2) {
		alert("Please ensure that From date is less than or equal to current date");
		$("#" + dataId + "").val("");
		$("#" + dataId + "").focus();
		return false;
	} else {
		return true;
	}

}

function clearTrainScreen() {
	if (confirm(clearMessage)) {
		document.trainColoadingForm.action = "./trainColoading.do?submitName=preparePageForTrain";
		document.trainColoadingForm.submit();
	}
}

function clearAirScreen() {
	if (confirm(clearMessage)) {
		document.airColoadingForm.action = "./airColoading.do?submitName=preparePage";
		document.airColoadingForm.submit();
	}
}

function clearVehicleScreen() {
	if (confirm(clearMessage)) {
		document.vehiclesContractColoadingForm.action = "./vehiclesContractColoading.do?submitName=preparePage";
		document.vehiclesContractColoadingForm.submit();
	}
}

function clearFuelRateEntryScreen() {
	if (confirm(clearMessage)) {
		document.fuelRateEntryForm.action = "./fuelRateEntryColoading.do?submitName=preparePage";
		document.fuelRateEntryForm.submit();
	}
}

function clearVehicleServiceEntryScreen() {
	if (confirm(clearMessage)) {
		document.vehicleServiceEntryForm.action = "./vehicleServiceEntry.do?submitName=preparePage";
		document.vehicleServiceEntryForm.submit();
	}
}

function clearSurfaceRateEntryScreen() {
	if (confirm(clearMessage)) {
		document.surfaceRateEntryForm.action = "./surfaceRateEntry.do?submitName=preparePage";
		document.surfaceRateEntryForm.submit();
	}
}

function loadSavedData() {
	if (!checkMandatory('Air')) {
		return;
	}
	
	var efDate = getValueByElementId("effectiveFrom");
	var vendorId = getValueByElementId("vendorList");
	
	var vendorList = [];

	var venList = $("#vendorList > option");
	venList.each(function() {
		vendorList.push(this.text + '=' + this.value);
	});
	
	var vendorName = $("#vendorList option:selected").text();
	
	document.airColoadingForm.action = "/udaan-config-admin/airColoading.do?submitName=loadAirDetails&efDate=" + efDate + "&vendorName=" + vendorName + 
	"&vendorId=" + vendorId + "&vendorList=" + vendorList;

	document.airColoadingForm.submit();
}

function loadVendorSavedData() {
	
	obj = document.getElementById("vendorId");
	if (isNull(obj.value)) {
		alert("Please select vendor name");
		obj.focus();
		return false;
	}
	
	document.surfaceRateEntryForm.action = "./surfaceRateEntry.do?submitName=loadVendorSavedData&vendorId=" + obj.value;
	document.surfaceRateEntryForm.submit();
}

function loadCitySavedData() {
	var obj = document.getElementById("effectiveFrom");
	if (isNull(obj.value)) {
		alert("Please provide effective from date");
		obj.focus();
		return false;
	}

	if (!checkDate('effectiveFrom')) {
		return false;
	}

	document.fuelRateEntryForm.action = "./fuelRateEntryColoading.do?submitName=loadExistingData";
	document.fuelRateEntryForm.submit();
}

function uploadTemplate(cdType) {
	var efDate = getValueByElementId("effectiveFrom");
	var vendorId = getValueByElementId("vendorList");
	var vendorName = $("#vendorList option:selected").text();
	
	if (!checkMandatory('Air')) {
		return;
	}
	
	var vendorList = [];

	var venList = $("#vendorList > option");
	venList.each(function() {
		vendorList.push(this.text + '=' + this.value);
	});
	
	if(cdType == 'AWB') {
		var sspWeightSlabValue = document.getElementById('sspWeightSlabList').value;
		if (isNullCheck(sspWeightSlabValue)) {
			alert('Please choose a SSP Rate Above value');
			return false;
		}
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
	progressBar();
	document.airColoadingForm.action = "/udaan-config-admin/airColoading.do?submitName=uploadXlsFile&efDate=" + efDate + "&vendorName=" + vendorName 
	+ "&vendorId=" + vendorId + "&vendorList=" + vendorList;
	document.airColoadingForm.submit();

}
function isNullCheck(value){
	var flag=true;
	if (value !=undefined && value!= null && value!=""  && value != "null" && value!=" "){
		flag = false;
	}
	return  flag;
	}

function checkMandatoryGridAirAWB() {
	var rowCount = $('#airColoadingTableAwb tr').length - 1;
	for ( var i = 1; i <= rowCount; i++) {
		if (!validateSelectRowAwb(i)) {
			return false;
		}
	}
	return true;
}

function checkMandatoryGridAirCD() {
	var rowCount = $('#airColoadingTableCd tr').length - 1;
	for ( var i = 1; i <= rowCount; i++) {
		if (!validateSelectRowCD(i)) {
			return false;
		}
	}
	return true;
}

function checkMandatoryGrid() {
	var rowCount = $('#trainDataTable tr').length - 1;
	var status = true;
	for ( var i = 1; i <= rowCount; i++) {
		if (!validateSelectRow(i)) {
			status = false;
		}
	}
	return status;
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
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("ratePerKG" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Rate Per KG." + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("otherChargesPerKG" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Other Charges Per KG." + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	return true;
}
/**
 * To validate AWB Excel data
 * 
 * @param rowId
 */
function validateSelectRowAwb(rowId) {
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
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("w1" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w1" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("w2" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w2" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("w3" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w3" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("w4" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w4" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("w5" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w5" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("w6" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct w6" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("fuelSurcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Fuel Surcharge" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("octroiPerBag" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per Bag" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("octroiPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per KG" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("originTSPFlatRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin TSP Flat Rate" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("originTSPPerKGRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin TSP Per KG Rate" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("destinationTSPFlatRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination TSP Flat Rate" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("destinationTSPPerKGRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination TSP Per KG Rate" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("airportHandlingCharges" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Airport Handling Charges" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("xRayCharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct X-Ray Charge" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("originMinUtilizationCharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin Min Utilization Charge" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("originUtilizationChargesPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Origin Utilization Charges Per KG"
				+ atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("destinationMinUtilizationCharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination Min Utilization Charge"
				+ atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("destinationUtilizationChargesPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Destination Utilization Charges Per KG"
				+ atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("serviceChargeOfAirline" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Service Charge Of Airline" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("surcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Surcharge" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("airWayBill" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Air Way Bill" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("discountedPercent" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Discounted Percent" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("sSPRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct SSP Rate" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
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
	} else {
		varObj.value = roundToTwo(varObj.value);
	}

	varObj = getDomElementById("fuelSurcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Fuel Surcharge" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("octroiPerBag" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per Bag" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("octroiPerKG" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Octroi Per KG" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("surcharge" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Surcharge" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("otherCharges" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct Other Charges" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("cd" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct CD" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	varObj = getDomElementById("sSPRate" + rowId);
	if (isNull(varObj.value) || isNaN(varObj.value)) {
		alert("Please provide correct SSP Rate" + atLine);
		varObj.focus();
		return false;
	} else {
		varObj.value = roundToTwo(varObj.value);
	}
	return true;
}

function loadTrainDetails(obj) {
	if (checkMandatory("Load")) {
		if (document.trainColoadingForm.action != undefined) {
			progressBar();
			
			var efDate = getValueByElementId("effectiveFrom");
			var vendorId = getValueByElementId("vendorList");
			
			var vendorList = [];

			var venList = $("#vendorList > option");
			venList.each(function() {
				vendorList.push(this.text + '=' + this.value);
			});
			
			var vendorName = $("#vendorList option:selected").text();
			
			document.trainColoadingForm.action = "/udaan-config-admin/trainColoading.do?submitName=loadTrainDetails&efDate=" + efDate + "&vendorName=" + vendorName + 
	"&vendorId=" + vendorId + "&vendorList=" + vendorList;
			
			document.trainColoadingForm.submit();
		}
	}
}

function searchVehicle(obj) {
	var obj = document.getElementById("vehicleNo");
	if (isNull(obj.value)) {
		alert("Please provide vehicle number");
		obj.focus();
		return false;
	}
	
	/*obj = document.getElementById("vendorList");
	if (isNull(obj.value)) {
		alert("Please select vendor name");
		obj.focus();
		return false;
	}*/
	
	obj = document.getElementById("effectiveFrom");
	if (isNull(obj.value)) {
		alert("Please provide effective from date");
		obj.focus();
		return false;
	}
	if (!checkDate('effectiveFrom')) {
		return false;
	}
	
	document.vehiclesContractColoadingForm.action = "./vehiclesContractColoading.do?submitName=searchAction";
	document.vehiclesContractColoadingForm.submit();

}

function roundToTwo(num) {
	return +(Math.round(num + "e+2") + "e-2");
}

function checkDecimals(fieldValue) {
	if (fieldValue.indexOf('.') == -1) 
		fieldValue += ".";
	dectext = fieldValue.substring(fieldValue.indexOf('.')+1, fieldValue.length);
	return dectext;
}		

function checkTwoOrMoreDots(num){
	var flag= false;
	if(num.indexOf('.', num.indexOf('.') + 1) != -1) {
		flag = true; //there are two or more dots in this string
	}
	return flag;
}

function roundToOne(num) {
	return Math.round(num*10)/10;
}

function checkStation(){
	eraseEffectivefromDate();
	
	var originRegionID = $("#origionRegionList");
	var destRegionID = $("#destinationRegionList");
	var originCityID = $("#origionStationList");
	var destCityID = $("#destinationStationList");
	
	if ((originRegionID.val() == destRegionID.val())
			&& originCityID.val() == destCityID.val()) {
		alert('Origin Station and Destination Station cannot be same.');
		destCityID.focus();
		$('#destinationStationList').prop('selectedIndex', 0);
		return false;
	}
}

function checkEffectivefrom(){
	var cdTypeList = document.getElementById("cdTypeList");
	cdTypeList.selectedIndex = 0;
	
	var vendorListID = $("#vendorList");
	var effectiveFromID = $("#effectiveFrom");
	if (effectiveFromID.val() == "" || effectiveFromID.val() == '-1') {
		alert('Select effective from date');
		vendorListID.focus();
		return false;
	}
}

function eraseEffectivefromDate(){
	var effectiveFromID = document.getElementById("effectiveFrom");
	effectiveFromID.value = "";

	var vendorListID = document.getElementById("vendorList");
	vendorListID.length = 0;
}

/*if(myString.indexOf('.', myString.indexOf('.') + 1) != -1) {
    System.out.print("it works"); //there are two or more dots in this string
}*/

