var PREV_SEL_DT_FIR = "";
var PREV_SEL_DT_TYPE = "";
var PREV_SEL_DT_LIR = "";
var PREV_SEL_DT_COF = "";
var PREV_SEL_DT_LOST_LETTER = "";
var CRTC_ERROR_FLAG = "ERROR";

/**
 * To view critical complaint - on tab click
 * 
 * @param cmplntNo
 */
function viewCriticalComplaint(cmplntNo) {
	getDomElementById("cmplntNo").value = cmplntNo;
	// jQuery("#complaintNo").val(cmplntNo);
	var complaintNo = cmplntNo;
	var url = "./criticalComplaint.do?submitName=viewCriticalComplaint&complaintNo="
			+ complaintNo;
	ajaxCall(url, criticalComplaintForm, callBackViewCriticalComplaint);
}
// call back function for viewCriticalComplaint
function callBackViewCriticalComplaint(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[CRTC_ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
		} else {
			var data = eval('(' + ajaxResp + ')');
			setCriticalCmpltDtls(data);
		}
	}
}

/**
 * To set critical complaints details
 * 
 * @param data
 */
function setCriticalCmpltDtls(data) {
	getDomElementById("complaintCreationDateStr").value = data.complaintCreationDateStr;
	getDomElementById("cmplntNo").value = data.complaintNo;
	getDomElementById("consignmentNumber").value = data.consignmentNumber;
	getDomElementById("branch").value = data.branch;
	getDomElementById("reason").value = data.reason;
	getDomElementById("declaredValue").value = data.declaredValue;
	getDomElementById("consignerName").value = data.consignerName;
	getDomElementById("customerCode").value = data.customerCode;
	getDomElementById("customerAddress").value = data.customerAddress;
	getDomElementById("customerPhone").value = data.customerPhone;
	getDomElementById("customerEmail").value = data.customerEmail;

	// Removed because dropdown was changed to txtbox
	/* prepareinfoGivenTO("informationGivenTo", data.infoGivenTO); */
	getDomElementById("informationGivenTo").value = data.informationGivenTo;

	getDomElementById("firDateStr").value = data.firDateStr;
	getDomElementById("focNumber").value = data.focNumber;
	getDomElementById("typeDateStr").value = data.typeDateStr;
	checkedCheckBox("firCopy", data.firCopy);
	checkedCheckBox("customerType", data.customerType);

	checkedCheckBox("lirCopy", data.lirCopy);
	getDomElementById("lirDateStr").value = data.lirDateStr;
	getDomElementById("lirRemarks").value = data.lirRemarks;
	checkedCheckBox("lostLetter", data.lostLetter);
	getDomElementById("lostLetterDateStr").value = data.lostLetterDateStr;
	getDomElementById("lostLetterRemarks").value = data.lostLetterRemarks;
	checkedCheckBox("cofCopy", data.cofCopy);
	getDomElementById("cofDateStr").value = data.cofDateStr;
	getDomElementById("cofRemarks").value = data.cofRemarks;
	setCheckBoxs();
	getDomElementById("remark").value = data.remark;

	getDomElementById("serviceRequestComplaintId").value = data.serviceRequestComplaintId;
	getDomElementById("cmplntId").value = data.complaintId;
	getDomElementById("mailerFileName").value = data.mailerFileName;
	getDomElementById("mailerCreatedDateStr").value = data.mailerCreatedDateStr;
	getDomElementById("mailerId").value = data.mailerId;
	getDomElementById("createdBy").value = data.createdBy;
	getDomElementById("updatedBy").value = data.updatedBy;
	getDomElementById("todaysDate").value = data.todaysDate;

	TODAY_DATE = data.todaysDate;

	getDomElementById("criticalCmpltStatus").value = data.criticalCmpltStatus;
}

/**
 * To checked check boxes according to values
 * 
 * @param objId
 * @param objVal
 */
function checkedCheckBox(objId, objVal) {
	getDomElementById(objId + "Yes").value = YES;
	getDomElementById(objId + "No").value = NO;
	if (objVal == YES) {
		getDomElementById(objId + "Yes").checked = true;
	} else {
		getDomElementById(objId + "No").checked = true;
	}
}

/**
 * To call this code on start up of critical complaint page
 */
function criticalComplaintStartup() {
	var serviceReqCmpltId = getDomElementById("serviceRequestComplaintId").value;
	if (isNull(serviceReqCmpltId)) {
		setBlankDates();
	} else {
		setCheckBoxs();
	}
}

/**
 * To set date blanks and radio buttons to NO
 */
function setBlankDates() {
	getDomElementById("firCopyNo").checked = true;
	getDomElementById("customerTypeNo").checked = true;
	getDomElementById("lirCopyNo").checked = true;
	getDomElementById("lostLetterNo").checked = true;
	getDomElementById("cofCopyNo").checked = true;
	setCheckBoxs();
}

/**
 * To make check boxes checked or unchecked
 */
function setCheckBoxs() {
	var firCopy = getDomElementById("firCopyYes");
	var customerType = getDomElementById("customerTypeYes");
	var lirCopy = getDomElementById("lirCopyYes");
	var lostLetter = getDomElementById("lostLetterYes");
	var cofCopy = getDomElementById("cofCopyYes");
	if (firCopy.checked) {
		$("#firDtStr").show();
		var firDateStr = getDomElementById("firDateStr").value;
		if (!isNull(firDateStr)) {
			PREV_SEL_DT_FIR = firDateStr;
		}
	} else {
		$("#firDtStr").hide();
		getDomElementById("firDateStr").value = "";
		PREV_SEL_DT_FIR = "";
	}

	if (customerType.checked) {
		$("#typeDtStr").show();
		var typeDateStr = getDomElementById("typeDateStr").value;
		if (!isNull(typeDateStr)) {
			PREV_SEL_DT_TYPE = typeDateStr;
		}
	} else {
		$("#typeDtStr").hide();
		getDomElementById("typeDateStr").value = "";
		PREV_SEL_DT_TYPE = "";
	}

	if (lirCopy.checked) {
		$("#lirDtStr").show();
		var lirDateStr = getDomElementById("lirDateStr").value;
		if (!isNull(lirDateStr)) {
			PREV_SEL_DT_LIR = lirDateStr;
		}
	} else {
		$("#lirDtStr").hide();
		getDomElementById("lirDateStr").value = "";
		PREV_SEL_DT_LIR = "";
	}

	if (lostLetter.checked) {
		$("#lostLetterDtStr").show();
		var lostLetterDateStr = getDomElementById("lostLetterDateStr").value;
		if (!isNull(lostLetterDateStr)) {
			PREV_SEL_DT_LOST_LETTER = lostLetterDateStr;
		}
	} else {
		$("#lostLetterDtStr").hide();
		getDomElementById("lostLetterDateStr").value = "";
		PREV_SEL_DT_LOST_LETTER = "";
	}

	if (cofCopy.checked) {
		$("#cofDtStr").show();
		var cofDateStr = getDomElementById("cofDateStr").value;
		if (!isNull(cofDateStr)) {
			PREV_SEL_DT_COF = cofDateStr;
		}
	} else {
		$("#cofDtStr").hide();
		getDomElementById("cofDateStr").value = "";
		PREV_SEL_DT_COF = "";
	}
}

/**
 * To set FIR Copy value to hidden field
 * 
 * @param obj
 */
function setFIRCopyValue(obj) {
	enableOrDisabledFIRCopyDate(obj);
}

/**
 * To set customer type value to hidden field
 * 
 * @param obj
 */
function setCustomerTypeValue(obj) {
	enableOrDisabledTypeDate(obj);
}

/**
 * To enable or disabled FIR copy date
 * 
 * @param obj
 */
function enableOrDisabledFIRCopyDate(obj) {
	if (obj.value == YES) { // If yes
		$("#firDtStr").show();
		if (isNull(PREV_SEL_DT_FIR)) {
			getDomElementById("firDateStr").value = TODAY_DATE;
		} else {
			getDomElementById("firDateStr").value = PREV_SEL_DT_FIR;
		}
	} else { // If No or blank
		$("#firDtStr").hide();
		getDomElementById("firDateStr").value = "";
		closeCalendarPopUp();
	}
}

/**
 * To enable or disabled type date
 * 
 * @param obj
 */
function enableOrDisabledTypeDate(obj) {
	if (obj.value == YES) { // If yes
		$("#typeDtStr").show();
		if (isNull(PREV_SEL_DT_TYPE)) {
			getDomElementById("typeDateStr").value = TODAY_DATE;
		} else {
			getDomElementById("typeDateStr").value = PREV_SEL_DT_TYPE;
		}
	} else { // If No or blank
		$("#typeDtStr").hide();
		getDomElementById("typeDateStr").value = "";
		closeCalendarPopUp();
	}
}

/**
 * To set date to text field
 * 
 * @param obj
 * @param focusId
 */
function setSelectedDate(obj, focusId) {
	show_calendar(focusId, obj.value);
}

/**
 * To validate selected date. it should less than current date
 * 
 * @param obj
 * @param focusId
 * @returns {Boolean}
 */
function validateSelectedDate(obj, focusId) {
	if (!isNull(obj.value)) {
		var arrSelectedDt = obj.value.split("/");// Selected date
		var selectedDt = new Date(arrSelectedDt[2], arrSelectedDt[1],
				arrSelectedDt[0]);
		var arrTodayDt = TODAY_DATE.split("/");// Current date
		var today = new Date(arrTodayDt[2], arrTodayDt[1], arrTodayDt[0]);
		var temp = obj.value;
		if (selectedDt > today) {
			var dateMsg = "";
			if (focusId == "firDateStr") {
				dateMsg = "FIR copy received date";
			} else if (focusId == "typeDateStr") {
				dateMsg = "Forwarded to client date";
			} else if (focusId == "lirDateStr") {
				dateMsg = "LIR copy received date";
			} else if (focusId == "lostLetterDateStr") {
				dateMsg = "Lost Letter Dispatched to Customer date";
			} else if (focusId == "cofDateStr") {
				dateMsg = "COF copy received date";
			}

			alert(dateMsg + " should not be greater than system date.");
			if (focusId == "firDateStr") {
				if (isNull(PREV_SEL_DT_FIR))
					PREV_SEL_DT_FIR = TODAY_DATE;
				obj.value = PREV_SEL_DT_FIR;
			} else if (focusId == "typeDateStr") {
				if (isNull(PREV_SEL_DT_TYPE))
					PREV_SEL_DT_TYPE = TODAY_DATE;
				obj.value = PREV_SEL_DT_TYPE;
			} else if (focusId == "lirDateStr") {
				if (isNull(PREV_SEL_DT_LIR))
					PREV_SEL_DT_LIR = TODAY_DATE;
				obj.value = PREV_SEL_DT_LIR;
			} else if (focusId == "lostLetterDateStr") {
				if (isNull(PREV_SEL_DT_LOST_LETTER))
					PREV_SEL_DT_LOST_LETTER = TODAY_DATE;
				obj.value = PREV_SEL_DT_LOST_LETTER;
			} else if (focusId == "cofDateStr") {
				if (isNull(PREV_SEL_DT_COF))
					PREV_SEL_DT_COF = TODAY_DATE;
				obj.value = PREV_SEL_DT_COF;
			}
			return false;
		}
		if (focusId == "firDateStr") {
			PREV_SEL_DT_FIR = temp;
		} else if (focusId == "typeDateStr") {
			PREV_SEL_DT_TYPE = temp;
		} else if (focusId == "lirDateStr") {
			PREV_SEL_DT_LIR = temp;
		} else if (focusId == "lostLetterDateStr") {
			PREV_SEL_DT_LOST_LETTER = temp;
		} else if (focusId == "cofDateStr") {
			PREV_SEL_DT_COF = temp;
		}
	}
}

/**
 * To submit form without prompt
 * 
 * @param url
 */
function submitFormWithoutPrompt(url) {
	document.criticalComplaintForm.action = url;
	document.criticalComplaintForm.submit();
}

/**
 * To save critical complaint details
 */
function saveCriticalComplaintDtls() {
	if (checkMandatoryFields()) {
		var url = "./criticalComplaint.do?submitName=saveOrUpdateCriticalComplaint";
		submitFormWithoutPrompt(url);
	}
}

/**
 * To clear the page.
 */
function clearPage() {
	getDomElementById("reason").value = "";
	getDomElementById("customerEmail").value = "";
	getDomElementById("informationGivenTo").value = "";
	getDomElementById("mailerFile").value = "";
	setBlankDates();
	getDomElementById("focNumber").value = "";
	getDomElementById("status").value = "";
	getDomElementById("remark").value = "";
}

/**
 * To check mandatory fields on save.
 * 
 * @returns {Boolean}
 */
function checkMandatoryFields() {
	var reason = getDomElementById("reason");
	// var email = getDomElementById("customerEmail");
	var infoGivenTo = getDomElementById("informationGivenTo");
	var criticalCmpltStatus = getDomElementById("criticalCmpltStatus");
	// var mailerFile = getDomElementById("mailerFile");
	if (isNull(trimString(reason.value))) {
		alert("Please provide \"Remarks\"");
		reason.value = "";
		setTimeout(function() {
			reason.focus();
		}, 10);
		return false;
	}
	/*
	 * if (isNull(trimString(email.value))) { alert("Please provid \"Email\"");
	 * email.value = ""; setTimeout(function() { email.focus(); }, 10); return
	 * false; }
	 */
	if (isNull(infoGivenTo.value)) {
		alert("Please provide \"Information Given To\"");
		setTimeout(function() {
			infoGivenTo.focus();
		}, 10);
		return false;
	}
	/*
	 * if (isNull(mailerFile.value)) { alert("Please upload \"Mailer File\"");
	 * setTimeout(function() { mailerFile.focus(); }, 10); return false; }
	 */
	if (isNull(trimString(criticalCmpltStatus.value))) {
		alert("Please select \"Status\"");
		criticalCmpltStatus.value = "";
		setTimeout(function() {
			criticalCmpltStatus.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * To prepare information give to drop down
 * 
 * @param selectId
 * @param list
 */
function prepareinfoGivenTO(selectId, list) {
	var selectOption = "--SELECT--";
	// clear drop down
	clearDropDownList(selectId, selectOption);
	// prepare drop down
	for ( var i = 0; i < list.length; i++) {
		addOptionToDropDown(selectId, list[i].description, list[i].stdTypeCode);
	}
}

/**
 * To add option to drop down
 * 
 * @param selectId
 * @param label
 * @param value
 */
function addOptionToDropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	var opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

/**
 * It deletes the drop down values of the fields
 * 
 * @param selectId
 */
function clearDropDownList(selectId, selectOption) {
	getDomElementById(selectId).options.length = 0;
	addOptionToDropDown(selectId, selectOption, "");
}

function setLIRCopyValue(obj) {
	enableOrDisabledLIRCopyDate(obj);
}

/**
 * To set COF Copy values
 * 
 * @param obj
 */
function setCOFCopyValue(obj) {
	enableOrDisabledCOFCopyDate(obj);
}

function setLostLetterValue(obj) {
	enableOrDisabledLostLetterDate(obj);
}

/**
 * To enable or disabled FIR copy date
 * 
 * @param obj
 */
function enableOrDisabledLIRCopyDate(obj) {
	if (obj.value == YES) { // If yes
		$("#lirDtStr").show();
		if (isNull(PREV_SEL_DT_LIR)) {
			getDomElementById("lirDateStr").value = TODAY_DATE;
		} else {
			getDomElementById("lirDateStr").value = PREV_SEL_DT_LIR;
		}
	} else { // If No or blank
		$("#lirDtStr").hide();
		getDomElementById("lirDateStr").value = "";
		closeCalendarPopUp();
	}
}

/**
 * To enable or disabled COF copy date
 * 
 * @param obj
 */
function enableOrDisabledCOFCopyDate(obj) {
	if (obj.value == YES) { // If yes
		$("#cofDtStr").show();
		if (isNull(PREV_SEL_DT_COF)) {
			getDomElementById("cofDateStr").value = TODAY_DATE;
		} else {
			getDomElementById("cofDateStr").value = PREV_SEL_DT_COF;
		}
	} else { // If No or blank
		$("#cofDtStr").hide();
		getDomElementById("cofDateStr").value = "";
		closeCalendarPopUp();
	}
}

/**
 * To enable or disabled type date
 * 
 * @param obj
 */
function enableOrDisabledLostLetterDate(obj) {
	if (obj.value == YES) { // If yes
		$("#lostLetterDtStr").show();
		if (isNull(PREV_SEL_DT_LOST_LETTER)) {
			getDomElementById("lostLetterDateStr").value = TODAY_DATE;
		} else {
			getDomElementById("lostLetterDateStr").value = PREV_SEL_DT_LOST_LETTER;
		}
	} else { // If No or blank
		$("#lostLetterDtStr").hide();
		getDomElementById("lostLetterDateStr").value = "";
		closeCalendarPopUp();
	}
}
