var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

function trackGatepass() {
	var cragNumObj = getDomElementById("number");
	var cragNum = trimString(cragNumObj.value);
	var type = getDomElementById("typename").value;

	if (isNull(type)) {
		clearFocusAlertMsg(getDomElementById("typename"), 'Please select type');
		return;
	}
	if (isNull(cragNum)) {
		clearFocusAlertMsg(cragNumObj, 'Please enter number');
		return;
	} else if (isValidNumber(cragNumObj)) {
		url = "./gatePassTracking.do?submitName=viewGatepassTrackInformation&type="
				+ type + "&number=" + cragNum;
		ajaxCalWithoutForm(url, populateCrag);
	}else{
		return;
	}
}
function populateCrag(data) {

	if (data != null) {
		var domObj = getDomElementById("number");
		var typeObj = getDomElementById("typename");
		
		var responseText = data;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			clearFocusAlertMsg(domObj, error);
			hideProcessing();
			return;
		}
		domObj.disabled = true;;
		typeObj.disabled = true;
		
		getDomElementById("originoffice").value = data.loadMovementTO.originOffice;
		getDomElementById("destination").value = data.loadMovementTO.destOffice;
		getDomElementById("bagDispatched").value = data.bagsDispatch;
		getDomElementById("bagReceived").value = data.bagsReceive;
		getDomElementById("mode").value = data.loadMovementTO.transportMode;
		getDomElementById("ftvNo").value = data.loadMovementTO.vehicleNumber;
		getDomElementById("dispatchedDate").value = data.dispatchDate;
		getDomElementById("receiveDate").value = data.receiveDate;
		getDomElementById("dispatchedwt").value = data.dispatchWt;
		getDomElementById("receivedwt").value = data.receiveWt;

		if (data.manifestTOs != null) {
			var dispDate = data.dispatchDate;
			var rcvDate = data.receiveDate;
			for ( var i = 0; i < data.manifestTOs.length; i++) {
				var childManifest = data.manifestTOs[i];				
				addChildManifestRows(childManifest,rcvDate,dispDate);
			}
		}
		getDomElementById("trackBtn").style.display = 'none';
	}
	else {
		alert("No details available");
	}
	hideProcessing();
}

function addChildManifestRows(childManifest,rcvDate,dispDate) {
	if(childManifest.manifestType == "R"){
		$('#example').dataTable().fnAddData(
				[ childManifest.manifestNumber, childManifest.mnfstWeight,
						childManifest.manifestStatus, rcvDate, childManifest.remarks ]);
	}else if(childManifest.manifestType == "D"){
		$('#example').dataTable().fnAddData(
				[ childManifest.manifestNumber, childManifest.mnfstWeight,
						childManifest.manifestStatus, dispDate, childManifest.remarks ]);
	}	
}

function isValidNumber(numberObj) {
	var type = getDomElementById("typename").value;
	if (type == 'GP') {
		return isValidGatepassNo(numberObj);
	}

	else if (type == 'CRAWB') {
		if (isNull(numberObj.value)) {
			return false;
		}
		if (trimString(numberObj.value).length > 10) {
			clearFocusAlertMsg(numberObj,
					"CD/RR/AWB No. should contain 10 characters only!");
			return false;
		} else {
			return true;
		}
	}
}
function isValidGatepassNo(gatepassNoObj) {
	// G+BranchCode(4 alphanumeric)+6digits :: RMUMB1234567
	gatepassNoObj.value = $.trim(gatepassNoObj.value);
	gatepassNoObj.value = gatepassNoObj.value.toUpperCase();

	var gatepassNo = gatepassNoObj.value;
	var numpattern = /^[0-9]+$/;
	var alphaNumeric = /^[A-Za-z0-9]+$/;
	var gatepassNoSubStr;

	if (isNull(gatepassNo)) {
		return false;
	}

	if (gatepassNo.length != 12) {
		clearFocusAlertMsg(gatepassNoObj,
				"Gatepass No. length must be of 12 characters!");
		return false;
	}

	gatepassNoSubStr = gatepassNo.substring(0, 1);
	if (gatepassNoSubStr != "G") {
		clearFocusAlertMsg(gatepassNoObj, "Gatepass No. must Starts with G!");
		return false;
	}

	gatepassNoSubStr = gatepassNo.substring(1, 5);
	if (!gatepassNoSubStr.match(alphaNumeric)) {
		clearFocusAlertMsg(gatepassNoObj, "Gatepass No. Format is not correct!");
		return false;
	}

	gatepassNoSubStr = gatepassNo.substring(5);
	if (!numpattern.test(gatepassNoSubStr)) {
		clearFocusAlertMsg(gatepassNoObj,
				"Gatepass No. last 7 characters must be digit!");
		return false;
	}

	return true;
}
function clearScreen(action) {
	var url = "./gatePassTracking.do?submitName=viewGatepassTracking";
	submitForm(url, action);
}

function submitForm(url, action) {
	if (confirm("Do you want to " + action + " details?")) {
		getDomElementById("number").value = "";
		document.gatepassTrackingForm.action = url;
		document.gatepassTrackingForm.submit();
	}
}
