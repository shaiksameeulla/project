var formId = "bulkCustModificationForm";
var PARTIAL_SUCCESS_FLAG = "PARTIAL_SUCCESS";

function bulkCnModificationStartUp(){
	fnEnableDisbleField('consgNos', true);
	$('#series').prop("checked", true);	
	$("#startConsgNo").focus();
}
function fnCheckCnModSelMode(selectionDom){
	var mode = selectionDom.value;
	$("#startConsgNo").val("");
	$("#endConsgNo").val("");
	$("#consgNos").val("");
	if(mode == 'S'){
		$('#series').val(mode);
		fnEnableDisbleField('startConsgNo', false);
		fnEnableDisbleField('endConsgNo', false);
		fnEnableDisbleField('consgNos', true);
		$("#startConsgNo").focus();
	}else{
		$('#multiple').val(mode);
		fnEnableDisbleField('startConsgNo', true);
		fnEnableDisbleField('endConsgNo', true);
		fnEnableDisbleField('consgNos', false);
		$("#consgNos").focus();
	}
}

function validateConsignment(domId){
	var consgNoObj = $("#"+domId);
	return isValidConsignment(consgNoObj);
}
function fnClear(){
	if(confirm("Do you want to clear the details?")){
		var url = "./bulkCustModification.do?submitName=preparePage";
		document.bulkCustModificationForm.action = url;
		document.bulkCustModificationForm.submit();
	}
}

function getStationByRegion() {
	var regionId = $("#regionName").val();
	if (!isNull(regionId)) {
		url = './bulkCustModification.do?submitName=getCitysByRegion&regionId='+ regionId;
		ajaxCallWithoutForm(url, populateCities);
	}
}

function populateCities(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var errorMsg = ajaxResp[ERROR_FLAG];
		if (!isNull(ajaxResp) && !isNull(errorMsg)) {
			showDropDownBySelected("stationList", "");
			alert(errorMsg);
			clearDropDownList("stationList");
			$('#stationList').focus();
		} else {
			printAjaxResList2Dropdown('stationList', ajaxResp);
			$('#stationList').focus();
		}
	}else{
		clearDropDownList("stationList");
	}
}

function fnMandatoryCkeck4BulkModification() {
	var isSeries = getDomElementById("series").checked;
	if (isSeries) {
		if (isNull($("#startConsgNo").val())) {
			focusAlertMsg4TxtBox($("#startConsgNo"), "Start CN number");
			return false;
		}
		if (isNull($("#endConsgNo").val())) {
			focusAlertMsg4TxtBox($("#endConsgNo"), "End CN number");
			return false;
		}
	} else {
		var number = trimString($("#consgNos").val());
		
		if (isNull(number)) {
			focusAlertMsg4TxtBox($("#consgNos"), "at least one consignment number");
			return false;
		}else{
			var text = (number).replace(/\n\r?/g, ',');			
			$("#bulkConsignmentsText").val(trimString(text));
		}
	}
	if (isNull($("#regionName").val())) {
		focusAlertMsg4TxtBox($("#regionName"), "region");
		return false;
	}
	if (isNull($("#stationList").val())) {
		focusAlertMsg4TxtBox($("#stationList"), "City");
		return false;
	}
	if (isNull($("#officeList").val())) {
		focusAlertMsg4TxtBox($("#officeList"), "Office");
		return false;
	}
	if (isNull($("#newCustName").val())) {
		focusAlertMsg4TxtBox($("#newCustName"), "the valid customer");
		return false;
	}
	if (isNull($("#bookingDate").val())) {
		focusAlertMsg4TxtBox($("#bookingDate"), "booking date");
		return false;
	}
	return true;
}
function saveOrUpdateBulkCnModification(){
	getAndSetCustomerDtls();
	if(fnMandatoryCkeck4BulkModification()){
		var url = "./bulkCustModification.do?submitName=saveOrUpdateBulkCustModification";
		ajaxJquery(url, formId, saveOrUpdateBulkCnModificationCallback);
	}
}
function saveOrUpdateBulkCnModificationCallback(response){
	if (!isNull(response)) {
		var result = jsonJqueryParser(response);
		var success = result[SUCCESS_FLAG];
		var partialSuccess = result[PARTIAL_SUCCESS_FLAG];
		var failure = result[ERROR_FLAG];
		if(!isNull(success)){
			alert(success);
			fnClear();
		}else if (!isNull(partialSuccess)) {
			if(confirm(partialSuccess)){
				var fileName ="bulk_consignment_ERROR.xlsx";
				//prepare the excel sheet
				fnDownloadErrorSheet(fileName);
			}else{
				fnClear();
			}			
		} else if (!isNull(failure)) {
			alert(failure);			
		} else {
			alert("Error in saving");
		}
	}
}

function fnDownloadErrorSheet(fileName) {
	window.open('bulkCustModification.do?submitName=getErrorCnList&fileName='+ fileName);
}