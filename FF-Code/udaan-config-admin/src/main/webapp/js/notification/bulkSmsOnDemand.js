$(document).ready(function() {
	var oTable = $('#dataGrid').dataTable({
		"sScrollY" : "230",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
		"bScrollCollapse" : false,
		"bSort" : false,
		"bInfo" : false,
		"bPaginate" : false,
		"sPaginationType" : "full_numbers"
	});
	new FixedColumns(oTable, {
		"sLeftWidth" : 'relative',
		"iLeftColumns" : 0,
		"iRightColumns" : 0,
		"iLeftWidth" : 0,
		"iRightWidth" : 0
	});
	/*if(getValueByElementId("officeType") == 'RO'){
		getDomElementById("region").disabled = true;
	}*/
	if(isNull(getValueByElementId("region"))){
		getDomElementById("region").focus();
	}else{
		getDomElementById("region").disabled = true;
		getDomElementById("city").focus();
	}		
});
var PREV_SEL_DT = "";
var ERROR_FLAG="ERROR";
var SUCCESS_FLAG="SUCCESS";
function getCitiesByRegion() {
	var regionId = "";
	createDropDown("city", "SELECT");
	createDropDown("office", "SELECT");
	regionId = getDomElementById("region").value;
	if (!isNull(regionId)) {
		showProcessing();
		url = './bulkSmsOnDemand.do?submitName=getCitiesByRegion&regionId='+ regionId;
		ajaxCallWithoutForm(url, getCitiesByRegionResponse);
	}
}

function getCitiesByRegionResponse(data) {
	var response = data;
	var errorMsg = getErrorMessage(response);
	if (!isNull(errorMsg)) {
		alert(errorMsg);
		return;
	}

	if (!isNull(response)) {
		var content = getDomElementById('city');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(response, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cityId;
			option.appendChild(document.createTextNode(this.cityName));
			content.appendChild(option);
		});
	} else {
		alert("Cities not found for the selected region");
	}
	hideProcessing();
}

function getOfficesByCity() {
	var cityId = "";
	createDropDown("office", "", "SELECT");
	cityId = getDomElementById("city").value;
	if (!isNull(cityId)) {
		showProcessing();
		url = './bulkSmsOnDemand.do?submitName=getOfficesByCity&cityId='+ cityId;
		ajaxCallWithoutForm(url, getOfficesByCityResponse);
	}
}

function getOfficesByCityResponse(data) {
	var response = data;
	var errorMsg = getErrorMessage(response);
	if (!isNull(errorMsg)) {
		alert(errorMsg);
		return;
	}

	if (!isNull(response)) {
		var content = getDomElementById('office');
		content.innerHTML = "";
		var defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		content.appendChild(defOption);
		$.each(response, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});
	} else {
		alert("Offices not found for the selected city");
	}
	hideProcessing();
}

function setDate(obj, focusId) {
	show_calendar(focusId, obj.value);
}
function validateDate(obj, focusId) {
	var arrSelectedDt = obj.value.split("/");// Selected date
	var selectedDt = new Date(arrSelectedDt[2], arrSelectedDt[1], arrSelectedDt[0]);
	var arrTodayDt = TODAY_DATE.split("/");// Current date
	var today = new Date(arrTodayDt[2], arrTodayDt[1], arrTodayDt[0]);
	var temp = obj.value;
	if (selectedDt > today) {
		var dateMsg = "";
		if (focusId == "fromDate") {
			dateMsg = "From Date";
		} else if (focusId == "toDate") {
			dateMsg = "To Date";
		}
		alert(dateMsg + " should not greater than system date.");
		if (isNull(PREV_SEL_DT))
			PREV_SEL_DT = TODAY_DATE;
		obj.value = PREV_SEL_DT;
		return false;
	}
	PREV_SEL_DT = temp;
	validateFromToDates(obj);
}
function validateFromToDates(obj){
	var fromDate = getValueByElementId("fromDate");
	var toDate = getValueByElementId("toDate");
	var i = compareDates(fromDate, toDate);
	if (i == 1) {
		alert("To Date should not be greater than from date.");
		setTimeout(function() {obj.focus();}, 10);
		obj.value = "";
		return false;
	}
}

function fnGetConsignmentDetails() {
	var url = "";
	if (validForm()) {
		url = './bulkSmsOnDemand.do?submitName=getConsignmentDetailsByStatus';
		showProcessing();
		jQuery.ajax({
			url : url,
			data : jQuery("#bulkSmsOnDemandForm").serialize(),
			success : function(req) {
				fnGetConsignmentDetailsResponse(req);
			}
		});
	}
}
function fnGetConsignmentDetailsResponse(data) {
	var response = jsonJqueryParser(data);
	if (!isNull(response)) {
		var failure = response[ERROR_FLAG];
		if (!isNull(failure)) {
			alert(failure);
			hideProcessing();
			return false;
		}
		var cnDetails = response.consignmentDtlTOs;
		var noofRows = cnDetails.length;
		inputDisable();
		dropdownDisable();
		disableGlobalButton("searchBtn");
		for ( var rowCnt = 1; rowCnt <= noofRows; rowCnt++) {
			populateGridDetails(rowCnt, cnDetails[rowCnt - 1]);
		}				
	}
	hideProcessing();
}

function populateGridDetails(rowcount, response){
	var reason = "";
	if(!isNull(response.reasonTO)){
		reason = response.reasonTO.reasonName;
	}
	$('#dataGrid').dataTable().fnAddData(
	[
	 rowcount,
	 response.consgNo,
	 response.bookingDate,
	 reason,
	 '<input type="text" class="txtbox" name="to.mobileNos" id="mobileNo' + rowcount+ '" size = "15" maxlength="10" onkeypress="return onlyNumeric(event);"'
		+ ' onchange="fnValidateMobileNo(this);" value="'+ response.mobileNo +'"/>'
		+ '<input type="hidden" name="to.consignmentNos" value="'+response.consgNo+'"/>'	 
	]);	
}

function validForm(){
	var officeDom = getDomElementById("office");
	var fromDateDom = getDomElementById("fromDate");
	var toDateDom = getDomElementById("toDate");
	var cnStatusDom = getDomElementById("cnStatus");
	
	if(isNull(officeDom.value)){
		alert("Please select Office");
		setTimeout(function() {officeDom.focus();}, 10);
		return false;
	}
	if(isNull(fromDateDom.value)){
		alert("Please select from date");
		setTimeout(function() {fromDateDom.focus();}, 10);
		return false;
	}
	if(isNull(toDateDom.value)){
		alert("Please select to date");
		setTimeout(function() {toDateDom.focus();}, 10);
		return false;
	}
	if(isNull(cnStatusDom.value)){
		alert("Please select CN status");
		setTimeout(function() {cnStatusDom.focus();}, 10);
		return false;
	}
	return true;
}
function fnValidateMobileNo(obj){
	if (!isNull(obj.value)) {
		var mobile = obj.value;
		var numpattern = /^[1-9][0-9]{0,9}$/;
		if (numpattern.test(mobile)) {
			if (mobile.length != 10) {
				alert("Enter 10 Digits Mobile Number");
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}
		} else {
			alert('Please enter valid mobile number');
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return false;
		}
	}
}
function fnSendBulkSMS(){
	var url = "./bulkSmsOnDemand.do?submitName=sendBulkSMS";
	showProcessing();
	jQuery.ajax({
		url : url,
		data : jQuery("#bulkSmsOnDemandForm").serialize(),
		success : function(req) {
			fnSendBulkSMSResponse(req);
		}
	});
}

function fnSendBulkSMSResponse(response){
	if (!isNull(response)) {
		var result = jsonJqueryParser(response);
		var success = result[SUCCESS_FLAG];
		var failure = result[ERROR_FLAG];
		if (!isNull(failure)) {
			alert(failure);
		} else if (!isNull(success)) {
			alert(success);		
		}
		hideProcessing();
	}
}

function fnClear(){
	if (confirm("Do you want to clear details?")) {
		var url = "./bulkSmsOnDemand.do?submitName=viewBulkSmsOnDemand";
		document.bulkSmsOnDemandForm.action = url;
		document.bulkSmsOnDemandForm.submit();
	}
}