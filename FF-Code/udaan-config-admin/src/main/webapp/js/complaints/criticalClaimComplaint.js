var PREV_SEL_DT = "";
var ERROR_FLAG = "ERROR";
/**
 * To set FIR Copy value to hidden field
 * 
 * @param obj
 */

function viewCriticalClaimComplaint(complaintNo) {
	getDomElementById("complaintNo").value = complaintNo;
	var url = "./criticalClaimComplaint.do?submitName=getCriticalClaimComplaintDtls";
	// submitFormWithoutPrompt(url);
	ajaxCall(url, criticalClaimComplaintForm, renderCriticalClaimComplaint);
}
// call back function for viewCriticalComplaint
function renderCriticalClaimComplaint(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {
			var data = eval('(' + ajaxResp + ')');
			setCriticalClaimComplaintDtls(data);
		}
	}
}

function hi() {
	alter('1');
}
function limitText(limitField, limitNum) {
	alter("infunction");
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	}
}

/**
 * To set critical complaints details
 * 
 * @param data
 */
function setCriticalClaimComplaintDtls(data) {
	getDomElementById("complaintNo").value = data.complaintNo;
	getDomElementById("complaintId").value = data.complaintId;
	getDomElementById("createdBy").value = data.createdBy;
	getDomElementById("updatedBy").value = data.updatedBy;
	getDomElementById("todaysDate").value = data.todaysDate;
	TODAY_DATE = data.todaysDate;
}

function setActualClaim(obj) {
	if (obj.checked) {
		getDomElementById("isActualClaim").value = obj.value;
		if (getDomElementById("isActualClaim").value == "N") {
			document.getElementById("actualClaimAmt").disabled = true;
			getDomElementById("actualClaimAmt").value = "";
		} else {
			document.getElementById("actualClaimAmt").disabled = false;
		}
	}
}

/**
 * To set customer type value to hidden field
 * 
 * @param obj
 */
function setNegotiableClaim(obj) {
	if (obj.checked) {
		getDomElementById("isNegotiableClaim").value = obj.value;
		if (getDomElementById("isNegotiableClaim").value == "N") {
			document.getElementById("negotiableClaimAmt").disabled = true;
			getDomElementById("negotiableClaimAmt").value = "";
		} else {
			document.getElementById("negotiableClaimAmt").disabled = false;
		}
	}
}

/**
 * To set COF field
 * 
 * @param obj
 */
function setCof(obj) {
	if (obj.checked) {
		getDomElementById("isCof").value = obj.value;
		if (getDomElementById("isCof").value == "N") {
			document.getElementById("cofAmt").disabled = true;
			getDomElementById("cofAmt").value = "";
		} else {
			document.getElementById("cofAmt").disabled = false;
		}
	}
}

/**
 * @param obj
 */
function setSettlement(obj) {
	if (obj.checked) {
		getDomElementById("isSettlement").value = obj.value;
	}
}

/**
 * @param obj
 */
function setSettled(obj) {
	if (obj.checked) {
		getDomElementById("isSettled").value = obj.value;
	}
}

/**
 * To set date to text field
 * 
 * @param obj
 * @param focusId
 */
function setDate(obj, focusId) {
	show_calendar(focusId, obj.value);
}

/**
 * To validate selected date. it should less than current date
 * 
 * @param obj
 * @param focusId
 * @returns {Boolean}
 */
function validateDate(obj, focusId) {
	var arrSelectedDt = obj.value.split("/");// Selected date
	var selectedDt = new Date(arrSelectedDt[2], arrSelectedDt[1],
			arrSelectedDt[0]);
	var arrTodayDt = TODAY_DATE.split("/");// Current date
	var today = new Date(arrTodayDt[2], arrTodayDt[1], arrTodayDt[0]);
	var temp = obj.value;
	if (selectedDt > today) {
		var dateMsg = "";
		if (focusId == "salesManagerFeedbackDate") {
			dateMsg = "Sales Manager Date";
		} else if (focusId == "csManagerFeedbackDate") {
			dateMsg = "Customer Service Manager date";
		} else if (focusId == "agmFeedbackDate") {
			dateMsg = "AGM date";
		} else if (focusId == "vpFeedBackDate") {
			dateMsg = "VP date";
		}
		alert(dateMsg + " should not greater than system date.");
		if (isNull(PREV_SEL_DT))
			PREV_SEL_DT = TODAY_DATE;
		obj.value = PREV_SEL_DT;
		return false;
	}
	PREV_SEL_DT = temp;
}

function cancelPage() {
	document.getElementById("actualClaimYes").checked = false;
	document.getElementById("actualClaimNo").checked = false;
	document.getElementById("negotiableClaimYes").checked = false;
	document.getElementById("negotiableClaimNo").checked = false;
	document.getElementById("settlementYes").checked = false;
	document.getElementById("settlementNo").checked = false;
	getDomElementById("actualClaimAmt").value = "";
	getDomElementById("negotiableClaimAmt").value = "";
	getDomElementById("paperWork").value = "";
	getDomElementById("accountability").value = "";
	getDomElementById("clientPolicy").value = "";
	getDomElementById("missingCertificate").value = "";
	getDomElementById("claimRemark").value = "";
	getDomElementById("salesManagerFeedback").value = "";
	getDomElementById("salesManagerFeedbackDate").value = "";
	getDomElementById("csManagerFeedback").value = "";
	getDomElementById("csManagerFeedbackDate").value = "";
	getDomElementById("agmFeedback").value = "";
	getDomElementById("agmFeedbackDate").value = "";
	getDomElementById("vpFeedback").value = "";
	getDomElementById("vpFeedBackDate").value = "";
}

function validateMandatoryFields() {
	// Actual Claim
	var actualClaim = getDomElementById("isActualClaim");
	var actualClaimAmount = getDomElementById("actualClaimAmt");
	if (isNull(actualClaim.value)) {
		alert("Please select Actual Claim.");
		setTimeout(function() {
			actualClaim.focus();
		}, 10);
		return false;
	}
	if (actualClaim.value == "Y") {
		if (isNull(actualClaimAmount.value)) {
			alert("Please enter Amount of Actual Claim.");
			setTimeout(function() {
				actualClaimAmount.focus();
			}, 10);
			return false;
		}
	}

	// Negotiable Claim
	var negotiableClaim = getDomElementById("isNegotiableClaim");
	var negotiableClaimAmt = getDomElementById("negotiableClaimAmt");
	if (isNull(negotiableClaim.value)) {
		alert("Please select Negotiable Claim.");
		setTimeout(function() {
			negotiableClaim.focus();
		}, 10);
		return false;
	}
	if (negotiableClaim.value == "Y") {
		if (isNull(negotiableClaimAmt.value)) {
			alert("Please enter Amount of Negotiable Claim.");
			setTimeout(function() {
				negotiableClaimAmt.focus();
			}, 10);
			return false;
		}
	}

	if (actualClaimAmount.value != undefined && actualClaimAmount.value != null
			&& actualClaimAmount.value != "null"
			&& actualClaimAmount.value != " "
			&& !isNull(negotiableClaimAmt.value)) {
		// alert("A C A "+actualClaimAmount.value);
		// alert("N C A "+negotiableClaimAmt.value);
		if (negotiableClaimAmt.value > actualClaimAmount.value) {
			alert("Negotiable Claim Amount should not be greater than Actual Claim Amount.");
			getDomElementById("negotiableClaimAmt").value = "";
			return false;
		}
	}

	// COF
	var cof = getDomElementById("isCof");
	var cofAmt = getDomElementById("cofAmt");
	if (isNull(cof.value)) {
		alert("Please select COF.");
		setTimeout(function() {
			cof.focus();
		}, 10);
		return false;
	}
	if (cof.value == "Y") {
		if (isNull(cofAmt.value)) {
			alert("Please enter Amount of COF.");
			setTimeout(function() {
				cofAmt.focus();
			}, 10);
			return false;
		}
	}

	var settlement = getDomElementById("isSettlement");
	if (isNull(settlement.value)) {
		alert("Please select Settlement.");
		setTimeout(function() {
			settlement.focus();
		}, 10);
		return false;
	}

	var isSettled = getDomElementById("isSettled");
	if (isNull(isSettled.value)) {
		alert("Please select Settled Yes/No.");
		setTimeout(function() {
			isSettled.focus();
		}, 10);
		return false;
	}

	return true;
}

function save(action) {
	if (validateMandatoryFields()) {
		if (action = "Save") {
			var url = "./criticalClaimComplaint.do?submitName=saveCriticalClaim";
			ajaxCall(url, criticalClaimComplaintForm,
					callBackSaveCriComplaintDtls);
			// submitReturn(url,'Save');
		}
	}
}

// AJAX call back function : saveCriticalComplaintDtls
function callBackSaveCriComplaintDtls(ajaxResp) {
	var response = ajaxResp;
	if (response != null && response == 'SUCCESS') {
		alert('Data saved successfully.');
		// clearFormData();
	} else {
		alert('Data saved Unsuccessfully. :: ' + response);
	}
}

function submitReturn(url, action) {
	if (confirm("Do you want to " + action
			+ " Critical Claim Complaint details?")) {
		document.criticalClaimComplaintForm.action = url;
		document.criticalClaimComplaintForm.submit();
	}
}

function getCriticalClaimComplaintDtls(complaintNumber) {
	enableAllInputElements();
	// alert('Helooooooooooooooooooooooooooo');
	var complaintNo = complaintNumber;
	if (isNull(complaintNo)) {
		alert("Cannot claim it as complaint has not got created");
		return false;
	}
	url = "./criticalClaimComplaint.do?submitName=getCriticalClaimComplaintDtls";
	ajaxCall(url, criticalClaimComplaintForm, populateCriticalComplaintDtls);
}

function populateCriticalComplaintDtls(ajaxResp) {
	if (!isNull(ajaxResp)) {
		// var responseText = ajaxResp;
		var responseText = eval('(' + ajaxResp + ')');
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			var txNo = getDomElementById("txnNo");
			txNo.value = "";
			setTimeout(function() {
				txNo.focus();
			}, 10);
			hideProcessing();
			return;
		}
		var criticalComplaitTORes = eval('(' + ajaxResp + ')');
		var criticalComplaitTO = criticalComplaitTORes;

		document.getElementById("todaysDate1").value = criticalComplaitTO.todaysDate;
		document.getElementById("serviceRequestComplaintId1").value = criticalComplaitTO.serviceRequestComplaintId;
		if (criticalComplaitTO.isActualClaim == "Y") {
			document.getElementById("actualClaimYes").checked = true;
		} else if (criticalComplaitTO.isActualClaim == "N") {
			document.getElementById("actualClaimNo").checked = true;
		} else {
			document.getElementById("actualClaimYes").checked = false;
			document.getElementById("actualClaimNo").checked = false;
		}
		if (criticalComplaitTO.isNegotiableClaim == "Y") {
			document.getElementById("negotiableClaimYes").checked = true;
		} else if (criticalComplaitTO.isNegotiableClaim == "N") {
			document.getElementById("negotiableClaimNo").checked = true;
		} else {
			document.getElementById("negotiableClaimYes").checked = false;
			document.getElementById("negotiableClaimNo").checked = false;
		}
		// COF
		if (criticalComplaitTO.isCof == "Y") {
			document.getElementById("cofYes").checked = true;
		} else if (criticalComplaitTO.isCof == "N") {
			document.getElementById("cofNo").checked = true;
		} else {
			document.getElementById("cofYes").checked = false;
			document.getElementById("cofNo").checked = false;
		}

		if (criticalComplaitTO.isSettlement == "LOCAL") {
			document.getElementById("settlementYes").checked = true;
		} else if (criticalComplaitTO.isSettlement == "CORP") {
			document.getElementById("settlementNo").checked = true;
		} else {
			document.getElementById("settlementYes").checked = false;
			document.getElementById("settlementNo").checked = false;
		}

		if (criticalComplaitTO.isSettled == "Y") {
			document.getElementById("isSettledYes").checked = true;
		} else if (criticalComplaitTO.isSettled == "N") {
			document.getElementById("isSettledNo").checked = true;
		} else {
			document.getElementById("isSettledYes").checked = false;
			document.getElementById("isSettledNo").checked = false;
		}

		document.getElementById("serviceReqClaimId").value = criticalComplaitTO.serviceReqClaimId;
		document.getElementById("actualClaimAmt").value = criticalComplaitTO.actualClaimAmt;
		document.getElementById("negotiableClaimAmt").value = criticalComplaitTO.negotiableClaimAmt;
		document.getElementById("cofAmt").value = criticalComplaitTO.cofAmt;
		document.getElementById("paperWork").value = criticalComplaitTO.paperWork;
		document.getElementById("accountability").value = criticalComplaitTO.accountability;
		document.getElementById("clientPolicy").value = criticalComplaitTO.clientPolicy;
		document.getElementById("missingCertificate").value = criticalComplaitTO.missingCertificate;
		document.getElementById("claimRemark").value = criticalComplaitTO.remark;
		document.getElementById("salesManagerFeedback").value = criticalComplaitTO.salesManagerFeedback;
		document.getElementById("salesManagerFeedbackDate").value = criticalComplaitTO.salesManagerFeedbackDate;
		document.getElementById("csManagerFeedback").value = criticalComplaitTO.csManagerFeedback;
		document.getElementById("csManagerFeedbackDate").value = criticalComplaitTO.csManagerFeedbackDate;
		document.getElementById("agmFeedback").value = criticalComplaitTO.agmFeedback;
		document.getElementById("agmFeedbackDate").value = criticalComplaitTO.agmFeedbackDate;
		document.getElementById("vpFeedback").value = criticalComplaitTO.vpFeedback;
		document.getElementById("vpFeedBackDate").value = criticalComplaitTO.vpFeedBackDate;
		document.getElementById("corporate").value = criticalComplaitTO.corporate;
		document.getElementById("corporateDate").value = criticalComplaitTO.corporateDate;
		getDomElementById("claimComplaintStatus").value = criticalComplaitTO.claimComplaintStatus;

		document.getElementById("isActualClaim").value = criticalComplaitTO.isActualClaim;
		document.getElementById("isNegotiableClaim").value = criticalComplaitTO.isNegotiableClaim;
		document.getElementById("isSettlement").value = criticalComplaitTO.isSettlement;
		document.getElementById("isSettled").value = criticalComplaitTO.isSettled;

		hideProcessing();
		disableAllInputElements();
	}
}

function submitURL(url) {

	document.criticalClaimComplaintForm.action = url;
	document.criticalClaimComplaintForm.method = "post";
	document.criticalClaimComplaintForm.submit();
}
