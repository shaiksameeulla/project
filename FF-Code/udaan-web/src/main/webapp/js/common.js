/*******************************************************************************
 * This source is part of the FLEXCUBE@ Java App Server Software System and is
 * copyrighted by Capgemini Consulting India private Limited. All rights
 * reserved. No part of this work may be reproduced, stored in a retrieval
 * system, adopted or transmitted in any form or by any means, electronic,
 * mechanical, photographic, graphic, optic recording or otherwise, translated
 * in any language or computer language, without the prior written permission of
 * Capgemini Consulting India private Limited.
 * 
 * Capgemini Consulting India private Limited. GODREJ INDUSTRIES COMPLEX,
 * VIKHROLI (E) Mumbai (ex Bombay)Maharashtra India Copyright © 2012 Capgemini
 * Consulting India private Limited Solutions Limited.
 * 
 * Modification History
 * 
 * Date Version Author Description __________ ___________ _______________
 * ________________________________________ 17-10-2012 1.1 Sameeulla created
 * re-usable script element 17-10-2012 1.2 Narasimha created re-usable script
 * elements
 * 
 ******************************************************************************/
// -----------------------------------------------------------------------------*/
// variables
var ERROR_FLAG = "ERROR";
var SUCCESS_FLAG = "SUCCESS";

var inputType = "input";
var hiddenType = "hidden";
var textAreaType = "textarea";
var checkBoxType = "checkbox";
var align = "center";
var BUTTON_DISABLE_CLASS = 'btnintformbigdis';
var BUTTON_ENABLE_CLASS = 'btnintform';
var GLOBAL_KEY_JSON = "KEY";
var GLOBAL_VALUE_JSON = "VALUE";

var OLD_CHILD_CN = new Array ();
// for duplicate field check

// Rate Calc Integrate
var productCode = "";
var consgType = "";
var originCity = "";
var destPincode = "";
var consgWeight = "";
var rateType = "";// FR,CC,BA,CH
var partyCode = "";
var declaredValue = "";
var discount = "";
var insuredBy = "";
var splCharges = "";
var originOfficeId = "";
// For duplicate consignment check
var usedConsignments = new Array();

// Process Code
var PROCESS_IN_MANIFEST_BAG_PARCEL = "IBPC";

var fieldArray = new Array();
function ajaxCall(url, formId, ajaxResponse) {
	jQuery.blockUI({
		message : '<h3><img src="images/loading_animation.gif"/></h3>'
	});
	jQuery.ajax({
		url : url,
		type : "POST",
		data : jQuery("#" + formId).serialize(),
		context : document.body,
		// success: function (data){getConsgDetailsResponse(data, rowNum,
		// type);}
		success : function(data) {
			jQuery.unblockUI();
			ajaxResponse(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			jQuery.unblockUI();
			alert(textStatus);
		}
	});
}

function ajaxCallWithoutForm(pageurl, ajaxResponse) {
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		success : function(data) {
			ajaxResponse(data);
		}
	});

}

function ajaxCallWithType(url, formId, ajaxResponse, type) {
	jQuery.blockUI({
		message : '<h3><img src="images/loading_animation.gif"/></h3>'
	});
	jQuery.ajax({
		url : url,
		data : jQuery("#" + formId).serialize(),
		context : document.body,
		dataType : type,
		// success: function (data){getConsgDetailsResponse(data, rowNum,
		// type);}
		success : function(data) {
			console.log("common.js - AJAX success");
			jQuery.unblockUI();
			ajaxResponse(data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			jQuery.unblockUI();
			console.log('common.js AJAX Error: ' + jqXHR + ', ' + textStatus
					+ ', ' + errorThrown);
		}
	});
}

function ajaxCallWithParam(url, formId, ajaxResponse, rowNum) {
	jQuery.ajax({
		url : url,
		data : jQuery("#" + formId).serialize(),
		context : document.body,
		success : function(data) {
			jQuery.unblockUI();
			ajaxResponse(data, rowNum);
		}
	});
}

function calculateDateDiff(fromDate, toDate) {
	var _fromDate = fromDate.split("/");
	var _toDate = toDate.split("/");
	// alert(" _heldUpDate &&&&& "+_heldUpDate);
	var _fromDateObj = new Date(_fromDate[2], _fromDate[1] - 1, _fromDate[0],
			00);
	var _toDateObj = new Date(_toDate[2], _toDate[1] - 1, _toDate[0], 00);
	var oneday = 24 * 60 * 60 * 1000;
	var calculateDate = _toDateObj - _fromDateObj;
	var dateDiff = Math.ceil(calculateDate / oneday);
	// alert(dateDiff);
	return dateDiff;
}

function calculateDateDiffExcludeSundays(fromDate, toDate) { // input given
	// as Date
	// objects
	var _fromDate = fromDate.split("/");
	var _toDate = toDate.split("/");
	var dDate1 = new Date(_fromDate[2], _fromDate[1] - 1, _fromDate[0], 00);
	var dDate2 = new Date(_toDate[2], _toDate[1] - 1, _toDate[0], 00);
	var iWeeks, iDateDiff, iAdjust = 0;
	if (dDate2 < dDate1)
		return -1; // error code if dates transposed
	var iWeekday1 = dDate1.getDay(); // day of week
	var iWeekday2 = dDate2.getDay();
	iWeekday1 = (iWeekday1 == 0) ? 7 : iWeekday1; // change Sunday from 0 to 7
	iWeekday2 = (iWeekday2 == 0) ? 7 : iWeekday2;
	if ((iWeekday1 > 6) && (iWeekday2 > 6))
		iAdjust = 1; // adjustment if both days on weekend
	iWeekday1 = (iWeekday1 > 6) ? 6 : iWeekday1; // only count weekdays
	iWeekday2 = (iWeekday2 > 6) ? 6 : iWeekday2;
	iWeeks = Math.floor((dDate2.getTime() - dDate1.getTime()) / 604800000);
	if (iWeekday1 <= iWeekday2) {
		iDateDiff = (iWeeks * 6) + (iWeekday2 - iWeekday1);
	} else {
		iDateDiff = ((iWeeks + 1) * 6) - (iWeekday1 - iWeekday2);
	}
	iDateDiff -= iAdjust; // take into account both days on weekend
	return iDateDiff; // add 1 because dates are inclusive
}

function isNull(value) {
	var flag = true;
	if (value != undefined && value != null && value != "" && value != "null"
			&& value != " " && value != "0") {
		flag = false;
	}
	return flag;
}

function isEmptyWeight(value) {
	var flag = false;
	if (isNull(value) || value == "0.000") {
		flag = true;
	}
	return flag;
}
function isEmptyRate(value) {
	var flag = false;
	if (isNull(value) || value == "0.00") {
		flag = true;
	}
	return flag;
}
function buttonDisabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).addClass(styleclass);
	jQuery("#" + btnName).attr("tabindex", -1);
}
function buttonEnabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).addClass(styleclass);
	jQuery("#" + btnName).removeAttr("tabindex");
}

function promptConfirmation(action) {
	return confirm("Do you want to " + action + " the details ?");
}

/**
 * 
 * @param id
 * @returns
 */
function getDomElementById(id) {
	return document.getElementById(id);
}

/**
 * 
 * @param id
 * @returns
 */
function getValueByElementId(id) {
	var dmId = getDomElementById(id);
	if (!isNull(dmId))
		return dmId.value;
	else
		return "";
}

function onlyNumeric(e) {
	var charCode;

	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else {
		return true;
	}
}
function onlyDecimal(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode != 46) {
		return false;
	} else {
		return true;
	}

}

function onlyAlphabet(e) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 46
			|| charCode == 32 || charCode == 9 || charCode == 8
			|| charCode == 127 || charCode == 37 || charCode == 39
			|| charCode == 0) {
		return true;
	} else {
		return false;
	}

}

function getRowId(domElement, elementName) {
	var id = domElement.id;
	var tokenArray = id.split(elementName);
	return tokenArray[1];
}

// Re-usable method – creating row
function createRow(name, id, size, type, inputType, isReadOnly, isDisabled,
		maxlength) {
	var element = document.createElement('"' + inputType + '"');
	element.type = type;
	element.name = name;
	element.id = id;
	element.size = size;
	if (isReadOnly)
		element.readOnly = true;
	if (isDisabled)
		element.disabled = true;
	if (maxlength != null && maxlength != "")
		element.maxlength = maxlength;
	return element;
}

/* Enter key navigation */
function callEnterKey(e, objectCn) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		objectCn.focus();
		return false;
	} else {

	}
}

/**
 * enterKeyNav
 * 
 * @param nextFieldId
 * @param keyCode
 * @returns {Boolean} Example : onkeydown="return
 *          enterKeyNav(nextFieldId,event.keyCode);"
 */
function enterKeyNav(nextFieldId, keyCode) {
	if (keyCode == 13) {
		// var nextField = document.getElementById(nextFieldId);
		// nextField.focus();
		focusById(nextFieldId);
		return true;
	}

}
/**
 * enterKeyNavWithoutFocus
 * 
 * @param keyCode
 * @returns {Boolean}
 */
function enterKeyNavWithoutFocus(keyCode) {
	if (keyCode == 13) {
		return true;
	}
	return false;
}

// To Check the selected date is future date
function isFutureDate(selectedDate) {

	var date = $.trim(selectedDate);
	if (date == "")
		return false;

	date = date.split("/");
	var myDate = new Date();
	myDate.setFullYear(date[2], date[1] - 1, date[0]);
	var today = new Date();
	if (myDate > today) {
		return true;
	}
	return false;
}
// To Check the selected date is Back date
function isBackDate(selectedDate) {
	var date = $.trim(selectedDate);
	if (date == "")
		return false;

	date = date.split("/");
	var myDate = new Date();
	myDate.setFullYear(date[2], date[1] - 1, date[0]);
	var today = new Date();
	if (myDate < today) {
		return true;
	}
	return false;
}
// Comapre two dates
function compareDates(date1, date2) {
	if (date1 == undefined || date2 == undefined) {
		return -100;// garbage value
	}
	date1 = date1.split("/");
	date2 = date2.split("/");

	var myDate1 = new Date();
	myDate1.setFullYear(date1[2], date1[1] - 1, date1[0]);

	var myDate2 = new Date();
	myDate2.setFullYear(date2[2], date2[1] - 1, date2[0]);
	if (myDate1 == myDate2) {
		return 0;
	} else if (myDate1 < myDate2) {
		return -1;
	} else if (myDate1 > myDate2) {
		return 1;
	}
}
// Phone number validation
function isValidPhone(phoneNumber) {
	if (phoneNumber == null) {
		return false;
	}
	// Expression for numeric values
	var objRegExp = /(^-?\d\d*$)/;

	if (objRegExp.test(phoneNumber)) {
		if (7 <= phoneNumber.length && phoneNumber.length <= 10) {
			return true;
		} else {
			return false;
		}
	} else {
		return false;
	}
}
// NAN Check validation
function isNANCheck(elementValue) {
	for ( var i = 0; i < elementValue.length; i++) {
		var cn = elementValue.charAt(i);
		if (isNaN(cn)) {
			return false;
		}
	}
	return true;
}
// show processing symbol
function showProcessing() {
	jQuery.blockUI({
		message : '<img src="images/loading_animation.gif"/>'
	});
}
function hideProcessing() {
	jQuery.unblockUI();
}

/** validation start for email */
function checkMailId(mailIds) {
	var mailId = mailIds.value;
	var val = true;
	var beforeat = "";
	var afterat = "";
	var afterat2 = "";

	var dot = mailId.lastIndexOf(".");
	var con = mailId.substring(dot, mailId.length);
	con = con.toLowerCase();
	con = con.toString();

	var att = mailId.lastIndexOf("@");
	beforeat = mailId.substring(0, att);
	beforeat = beforeat.toLowerCase();
	beforeat = beforeat.toString();
	var asci1 = beforeat.charCodeAt(0);

	afterat = mailId.substring(att + 1, dot);
	afterat = afterat.toLowerCase();
	afterat = afterat.toString();

	afterat2 = mailId.substring(att + 1, mailId.length);
	afterat2 = afterat2.toLowerCase();
	afterat2 = afterat2.toString();

	if (beforeat == "" || afterat == "" || beforeat.length > 30)
		val = false;

	if (afterat2.length > 64 || afterat.length < 2)
		val = false;

	if ((afterat.charCodeAt(0)) == 45
			|| (afterat.charCodeAt(afterat.length - 1)) == 45)
		val = false;

	if (val == true) {
		if (asci1 > 47 && asci1 < 58)
			val = false;

		if (asci1 < 48 || asci1 > 57) {
			for ( var i = 0; i <= beforeat.length - 1; i++) {
				var asci2 = beforeat.charCodeAt(i);
				if ((asci2 <= 44 || asci2 == 47)
						|| (asci2 >= 58 && asci2 <= 94) || (asci2 == 96)
						|| (asci2 >= 123 && asci2 <= 127)) {
					val = false;
					break;
				}
			}

			for ( var j = 0; j <= afterat.length - 1; j++) {
				var asci3 = afterat.charCodeAt(j);
				if ((asci3 <= 44) || (asci3 == 46) || (asci3 == 47)
						|| (asci3 >= 58 && asci3 <= 96)
						|| (asci3 >= 123 && asci3 <= 127)) {
					val = false;
					break;
				}
			}
		}
	}

	if (val == false) {
		if (mailId != "null" && mailId != "") {
			alert("Mail Id is not valid");
			mailIds.value = "";
			setTimeout(function() {
				document.getElementById(mailIds.id).focus();
			}, 10);
			return false;
		} else {
			return true;
		}
	} else {
		return true;
	}
}
function alphaNumericValidate(alphanumericChar) {
	if (alphanumericChar.search(/[^a-zA-Z0-9 ]/g) != -1) {
		return false;
	} else {
		return true;
	}
}
function alphaValidate(alphaChar) {
	if (alphaChar.search(/[^a-zA-Z]/g) != -1) {
		return false;
	} else {
		return true;
	}
}
function mandatoryFieldCheck(obj, name) {
	if (obj.value == null || obj.value == "") {
		alert("Please enter " + name);
		// setTimeout(function(){document.getElementById(obj.id).focus();},10);
	}
}

function checkNull(obj) {
	if (obj != "" && obj != null && obj != "null" && obj != undefined) {
		return false;
	} else {
		return true;
	}
}
// Validate Time in HH:MM format
function isValidTimeHHMM(obj, objvalue) {
	if (objvalue != null && objvalue != "") {
		var timRegX = /^(\d{1,2}):(\d{2})?$/;

		var timArr = objvalue.match(timRegX);
		if (timArr == null) {
			alert("Time is not in a valid format.");
			document.getElementById(obj.id).value = "";
			document.getElementById(obj.id).focus();
			return false;
		}
		hour = timArr[1];
		minute = timArr[2];

		if (hour < 0 || hour > 23) {
			alert("Hour must be between 1 and 12. (or 0 and 23 for 24 hours time)");
			return false;
		}

		if (minute < 0 || minute > 59) {
			alert("Minute must be between 0 and 59.");
			return false;
		}
	}
	return false;
}
function getEndSerialNumber(stSlNo, quantity) {
	var zeros = 0;
	var nonZeros = 0;
	var zeroDeletions = 0;
	var count = getCountFromString(stSlNo);
	var startNum = stSlNo.substring(count, stSlNo.length);
	// get substring from count till quantity.length
	var startchar = stSlNo.substring(0, count);

	// alert("startchar"+startchar);

	if (startNum != null && trimString(startNum) != '') {
		// get substring from count till quantity.length
		var startchar = stSlNo.substring(0, count);
		var EndNum = parseInt(quantity, 10) + parseInt(startNum, 10);
		if (EndNum > 0)
			EndNum = EndNum - 1;
		// perform trailing zeros --start
		var startNumSize = startNum.length;
		var afterParsing = parseInt(startNum, 10) + "";
		var afterParsingSize = afterParsing.length;
		var endNumva = EndNum + "";
		var EndNumsize = endNumva.length;
		if (startNumSize > afterParsingSize) {
			// alert("sss");
			var startArray = new Array();
			for ( var i = 0; i < startNumSize; i++) {
				startArray[i] = startNum.charAt(i);
				if (startArray[i] == "0") {
					++zeros;
				} else
					++nonZeros;
			}

			// alert("Total Zeros"+zeros);
			// alert("NonZeros"+nonZeros);
			if (EndNumsize > nonZeros) {
				zeroDeletions = EndNumsize - nonZeros;
			}
			if (zeroDeletions > 0) {
				zeros = zeros - zeroDeletions;
			}
			for ( var i = 0; i < zeros; i++)
				EndNum = "0" + EndNum;
		}
		// perform trailing zeros --end

		var EndSlNum = startchar + EndNum;
		// alert("start Num"+stSlNo+"\t"+"end serial Num"+EndSlNum);
		return EndSlNum;

	} else {
		alert("Serial number is having incorrect format");
		return false;
	}
}
function getCountFromString(str) {
	var count11 = str.length;
	for (k = str.length - 1; k >= 0; k--) {
		var charCode = str.charCodeAt(k);
		if (charCode > 31 && (charCode < 48 || charCode > 57)) {
			break;
		}
		count11--;
	}
	return count11;
}

// added for preventing duplicate Field being added
function checkDuplicateField(obj, fieldName) {
	var uniqueField = true;
	var fieldValue = obj.value;
	var rowId = getRowId(obj, obj.id);
	if (fieldArray != null && fieldArray.length != 0) {
		for ( var i = 0; i < fieldArray.length; i++) {
			if (fieldValue != null && fieldValue != ""
					&& fieldArray[i] == fieldValue && rowId != i) {
				alert(fieldName + " already entered !");
				document.getElementById(obj.id).value = "";
				document.getElementById(obj.id).focus();
				uniqueField = false;
			}
		}
		if (uniqueField) {
			fieldArray[rowId] = fieldValue;
		}
	} else {
		if (fieldArray.length == 0) {
			fieldArray[rowId] = fieldValue;
		}
	}
	return uniqueField;
}
// gives u last row Id by element name
function getTableLastRowIdByElement(tableId, elementName, elementId) {
	var rowCount = null;
	var tableee = document.getElementById(tableId);
	var cell = document.getElementsByName(elementName);
	var cntofRow = tableee.rows.length;
	if (cntofRow > 2) {
		var dom = cell[cntofRow - 2];
		rowCount = getRowId(dom, elementId);
	}
	return rowCount;
}
// check whether check box is selected or not
function isCheckBoxSelected(chkboxElem) {
	return isCheckBoxSelectedWithMessage(chkboxElem, 'CheckBox is not selected');
}

function isCheckBoxSelectedWithMessage(chkboxElem, message) {
	var checkFalg = 0;
	var box = document.getElementsByName(chkboxElem);
	for ( var i = 0; i < box.length; i++) {
		if (box[i].checked) {
			checkFalg = 1;
			break;
		} else {
			checkFalg = 0;
		}
	}
	if (checkFalg == 0) {
		alert(message);
		return false;
	}
	return true;
}

function checkAllBoxes(ckbxsName, checkedVal) {
	var box = document.getElementsByName(ckbxsName);
	// alert("box.length"+box.length);
	if (box != null && box.length > 0) {
		for ( var i = 0; i < box.length; i++) {
			if (checkedVal == true) {
				box[i].checked = true;
			} else if (checkedVal == false) {
				box[i].checked = false;
			}
		}
	}
}
function convertToUpperCase(val) {
	return val.toUpperCase();
}
function getTableLength(tableId) {
	var tableee = getDomElementById(tableId);
	return tableee.rows.length;
}
function checkForDuplicateSerialNumber(startSlNum, endSlNum, currentRowId,
		tableId, domElementId1, domElementId2) {

	var currstartSiDom = document.getElementById(domElementId1 + currentRowId);
	var currEnSiDom = document.getElementById(domElementId2 + currentRowId);
	var leng = getTableLength(tableId);
	var count = getCountFromString(startSlNum);
	var officeProd = startSlNum.substring(0, count);
	officeProd = convertToUpperCase(officeProd);
	var currStNum = startSlNum.substring(count, startSlNum.length);// alert("currStNum"
	// +currStNum);
	var currEndNum = endSlNum.substring(count, endSlNum.length);// alert("currEndNum"
	// +currEndNum);

	var _currStNum = parseInt(currStNum, 10);// getting start SI
	// Number,number part and
	// parsing that number
	var _currEndNum = parseInt(currEndNum, 10);// getting end End Number,number
	// part and parsing that number

	for ( var i = 1; i < leng; i++) {

		var stSI = document.getElementById(domElementId1 + i);
		var prevStSi = stSI.value;
		if (!isNull(prevStSi)) {
			var countP = getCountFromString(prevStSi);
			var officeProdPr = prevStSi.substring(0, countP);
			officeProdPr = convertToUpperCase(officeProdPr);
			if (currentRowId != i && officeProdPr == officeProd) {
				var prevEndSi = document.getElementById(domElementId2 + i).value;
				var prvStNum = prevStSi.substring(countP, prevStSi.length);// alert("prvStNum"
				// +prvStNum);
				var prvEndNum = prevEndSi.substring(countP, prevEndSi.length);// alert("prvEndNum"
				// +prvEndNum);
				var _prvStNum = parseInt(prvStNum, 10);// getting start SI
				// Number,number part
				// and parsing that
				// number
				var _prvEndNum = parseInt(prvEndNum, 10);// getting end End
				// Number,number
				// part and parsing
				// that number

				if (((_currStNum >= _prvStNum) && (_currStNum <= _prvEndNum))
						|| ((_currEndNum >= _prvStNum) && (_currEndNum <= _prvEndNum))) {
					alert("Start/End serial number already exist at the line :"
							+ i);
					currstartSiDom.value = "";
					currEnSiDom.value = "";
					return false;
				} else if (((_currStNum <= _prvStNum) && (_currEndNum >= _prvEndNum))
						|| (((_currStNum <= _prvStNum) && (_prvStNum <= _currEndNum)) && ((_currStNum <= _prvEndNum) && (_prvEndNum <= _currEndNum)))) {
					alert("Start/End serial number already exist at the line :"
							+ i);
					currstartSiDom.value = "";
					currEnSiDom.value = "";
					return false;
				}

			}
		}

	}
	return true;

}
function jsonJqueryParser(jsonObj) {
	return jQuery.parseJSON(jsonObj);
}
function buttonDisable() {
	jQuery(":button").attr("disabled", true);
	jQuery(":button").addClass(BUTTON_DISABLE_CLASS);

}
function inputDisable() {
	jQuery(":input").attr("readonly", true);

}
function dropdownDisable() {
	jQuery("select").attr("disabled", 'disabled');
	jQuery("select").attr("tabindex", -1);

}

function dropdownEnable() {
	jQuery("select").attr("disabled", false);
	jQuery("select").removeAttr("tabindex");

}
function disableAll() {
	buttonDisable();
	inputDisable();
	dropdownDisable();
}
function ajaxJquery(url, formId, ajaxResponseMethod) {
	ajaxJqueryWithRow(url, formId, ajaxResponseMethod, null);
}
function ajaxJqueryWithRow(url, formId, ajaxResponseMethod, rowId) {
	progressBar();
	$.ajax({
		url : url,
		type : "POST",
		dataType : "text",
		data : jQuery("#" + formId).serialize(),
		success : function(rsp) {
			// alert("success");
			jQuery.unblockUI();
			if (isNull(rowId)) {
				ajaxResponseMethod(rsp);
			} else {
				ajaxResponseMethod(rsp, rowId);
			}
		},
		error : function(rsp) {
			// alert('error');
			if (isNull(rowId)) {
				ajaxResponseMethod(rsp);
			} else {
				ajaxResponseMethod(rsp, rowId);
			}
			jQuery.unblockUI();
		}
	});
	// jQuery.unblockUI();
}

function progressBar() {
	jQuery.blockUI({
		message : '<h3><img src="images/loading_animation.gif"/></h3>'
	});
}

function updateSrlNo(tableID) {
	try {
		var table = document.getElementById(tableID);
		for ( var i = 1; i < rowCount; i++) {
			var row = table.rows[i];
			var val = row.cells[1];
			val.innerHTML = i;
		}
	} catch (e) {
		// alert(e);
	}
}

function getCNCount(startNo, endNo) {
	var cnCount = 0;
	if (!isNull(startNo) && !isNull(endNo)) {
		if (startNo >= endNo) {
			alert("Start CN number should not higher than End number!.");
			return;
		} else if (endNo <= startNo) {
			alert("End CN number should not lower than Start number!.");
			return;
		} else {
			cnCount = parseInt(endNo) - parseInt(startNo);
		}
	}
	return cnCount;
}

function parseIntNumber(number) {
	return (!isNull(number) ? (parseInt(number, 10)) : 0);
}
function createDropDown(domId, resList) {
	var domElement = getDomElementById(domId);
	domElement.options.length = 0;
	var optionSelectType = document.createElement("OPTION");
	// var text = document.createTextNode("--Select--");
	optionSelectType.value = "";
	optionSelectType.text = "--Select--";
	// domElement.add(optionSelectType,null);
	try {
		domElement.add(optionSelectType, null);
	} catch (e) {
		domElement.add(optionSelectType);
	}
	// var domElement = createEmptyDropDown(domId);
	var newStr = resList.replace("{", "");
	var newStr1 = newStr.replace("}", "");
	var keyValList = newStr1.split(",");
	for ( var i = 0; i < keyValList.length; i++) {
		var objOption = document.createElement("option");
		var keyValPair = keyValList[i].split("=");
		objOption.text = trimString(keyValPair[1]);
		objOption.value = trimString(keyValPair[0]);
		// domElement.add(objOption,null);
		// modified for Browser compatibility(By sami)
		try {
			domElement.add(objOption, null);
		} catch (e) {
			domElement.add(objOption);
		}
	}
	// domElement.setAttribute("style","width: 100px");
	return domElement;
}

function createEmptyDropDown(domId) {
	var domElement = getDomElementById(domId);
	domElement.options.length = 0;
	var optionSelectType = document.createElement("OPTION");
	// var text = document.createTextNode("--Select--");
	optionSelectType.value = "";
	optionSelectType.text = "--Select--";
	// modified for Browser compatibility(By sami)
	try {
		domElement.add(optionSelectType, null);
	} catch (e) {
		domElement.add(optionSelectType);
	}
	return domElement;
}
/**
 * createEmptyDropDown
 * 
 * @param domId
 * @returns {___domElement4}
 */
function createEmptyDropDownWithAllOption(domId) {
	var domElement = getDomElementById(domId);
	createEmptyDropDown(domId);
	createOnlyAllOption(domId);
	return domElement;
}

function createOnlyAllOption(domId) {
	return createOnlyAllOptionWithValue(domId, "-1");
}
function createOnlyAllOptionWithValue(domId, param) {
	var domElement = getDomElementById(domId);
	var alloption = document.createElement("OPTION");
	// var text = document.createTextNode("ALL");
	alloption.value = param;
	alloption.text = "ALL";
	// modified for Browser compatibility(By sami)
	try {
		domElement.add(alloption, null);
	} catch (e) {
		domElement.add(alloption);
	}
	return domElement;
}
function clearDropDown(elementId) {
	getDomElementById(elementId).options.length = 0;

}

/*
 * function move(tbFrom, tbTo) { var arrFrom = new Array(); var arrTo = new
 * Array(); // to store text of tbTo var arrLU = new Array(); // to store values
 * of tbTo var i; for (i = 0; i < tbTo.options.length; i++) { arrLU[i] =
 * tbTo.options[i].value; arrTo[i] = tbTo.options[i].text; } var fLength = 0;
 * var tLength = arrTo.length; for (i = 0; i < tbFrom.options.length; i++) {
 * arrLU[i] = tbFrom.options[i].value; if (tbFrom.options[i].selected &&
 * tbFrom.options[i].value != "") { arrTo[tLength] = tbFrom.options[i].text;
 * tLength++; } else { arrFrom[fLength] = tbFrom.options[i].text; fLength++; } }
 * 
 * tbFrom.length = 0; tbTo.length = 0; var ii;
 * 
 * for (ii = 0; ii < arrFrom.length; ii++) { var no = new Option(); no.value =
 * arrLU[arrFrom[ii]]; no.text = arrFrom[ii]; tbFrom[ii] = no; }
 * 
 * for (ii = 0; ii < arrTo.length; ii++) { var no = new Option(); no.value =
 * arrLU[arrTo[ii]]; no.text = arrTo[ii]; tbTo[ii] = no; } }
 */

// Rewritten move() function since the exististing logic was not working
// properly..
function move(tbFrom, tbTo) {
	var arrFromText = new Array(); // to store text of tbFrom
	var arrFromValues = new Array(); // to store values of tbFrom

	var arrToText = new Array(); // to store text of tbTo
	var arrToValues = new Array(); // to store values of tbTo

	// prepare array of TO array
	var i;
	for (i = 0; i < tbTo.options.length; i++) {
		arrToValues[i] = tbTo.options[i].value;
		arrToText[i] = tbTo.options[i].text;
	}
	var fLength = 0;
	var tLength = arrToText.length;
	for (i = 0; i < tbFrom.options.length; i++) {
		// add the selected elements to the TO array
		if (tbFrom.options[i].selected && tbFrom.options[i].value != "") {
			arrToValues[tLength] = tbFrom.options[i].value;
			arrToText[tLength] = tbFrom.options[i].text;
			tLength++;
		} else {
			// prepare array of FROM array
			arrFromText[fLength] = tbFrom.options[i].text;
			arrFromValues[fLength] = tbFrom.options[i].value;
			fLength++;
		}
	}

	tbFrom.length = 0;
	tbTo.length = 0;
	var ii;

	// Clear and Copy FROM array in tbFrom variable
	for (ii = 0; ii < arrFromText.length; ii++) {
		var no = new Option();
		no.value = arrFromValues[ii];
		no.text = arrFromText[ii];
		tbFrom[ii] = no;
	}

	// Clear and Copy TO array in tbTo variable
	for (ii = 0; ii < arrToText.length; ii++) {
		var no = new Option();
		no.value = arrToValues[ii];
		no.text = arrToText[ii];
		tbTo[ii] = no;
	}
}

function isExistInArray(value, array) {
	return jQuery.inArray(value, array);
}

function inputEnable() {
	jQuery(":input").attr("readonly", false);
	// commented by sami, since Input type does not have such attribute
	// uncommented by Narmdeshwar, sami please don't comment any common
	// functionality it breaks others code.
	jQuery(":input").attr("disabled", false);
}
function buttonEnable() {
	jQuery(":button").attr("disabled", false);
	jQuery(":button").addClass(BUTTON_ENABLE_CLASS);

}
function buttonEnableById(fieldId) {
	document.getElementById(fieldId).disabled = false;
	jQuery("#" + fieldId).removeClass(BUTTON_DISABLE_CLASS);
	jQuery("#" + fieldId).addClass(BUTTON_ENABLE_CLASS);
}
function buttonDisableById(fieldId) {
	jQuery("#" + fieldId).attr("disabled", true);
	jQuery("#" + fieldId).addClass(BUTTON_DISABLE_CLASS);
}

function enableAll() {
	buttonEnable();
	inputEnable();
	dropdownEnable();
}
/**
 * getSelectedDropDownTextByDOM
 * 
 * @param domElement
 * @returns
 */
function getSelectedDropDownTextByDOM(domElement) {
	var dropDownDom = domElement;
	var selectedIndex = dropDownDom.selectedIndex;
	return dropDownDom.options[selectedIndex].text;
}
/**
 * getSelectedDropDownText
 * 
 * @param selectId
 * @returns
 */
function getSelectedDropDownText(selectId) {
	var dropDownDom = getDomElementById(selectId);
	return getSelectedDropDownTextByDOM(dropDownDom);
}
/**
 * trimString : to trim string
 * 
 * @param str
 * @returns
 */
function trimString(str) {
	var result = "";
	if (!isNull(str) && jQuery.type(str) == "string") {
		try {
			result = $.trim(str);
		} catch (e) {
			result = str;
		}
	}
	return result;
}

function calcConsgRate(rowCount) {
	showProcessing();
	url = './cashBooking.do?submitName=calcCNRate&productCode=' + productCode
			+ "&consgType=" + consgType + "&originCity=" + originCity
			+ "&destPincode=" + destPincode + "&consgWeight=" + consgWeight
			+ "&rateType=" + rateType + "&partyCode=" + partyCode
			+ "&declaredValue=" + declaredValue + "&discount=" + discount
			+ "&insuredBy=" + insuredBy + "&splCharges=" + splCharges
			+ "&originOfficeId=" + originOfficeId;
	jQuery.ajax({
		url : url,
		type : "POST",
		success : function(req) {
			printCallBackCNRateDetails(req, rowCount);
		}
	});
}

/**
 * convertToFraction
 * 
 * @param value
 * @param digits
 * @returns
 * @author sdalli
 */
function convertToFraction(value, digits) {
	var val = parseFloat(value).toFixed(digits);
	return val;
}

function validateEmail(addr) {
	if (addr == '') {
		alert('email address is mandatory');
		return false;
	}

	var invalidChars = '\/\'\\ ";:?!()[]\{\}^|';
	for (i = 0; i < invalidChars.length; i++) {
		if (addr.indexOf(invalidChars.charAt(i), 0) > -1) {
			alert('email address contains invalid characters');
			return false;
		}
	}
	for (i = 0; i < addr.length; i++) {
		if (addr.charCodeAt(i) > 127) {
			alert("email address contains non ascii characters.");
			return false;
		}
	}

	var atPos = addr.indexOf('@', 0);
	if (atPos == -1) {
		alert('email address must contain an @');
		return false;
	}
	if (atPos == 0) {
		alert('email address must not start with @');
		return false;
	}
	if (addr.indexOf('@', atPos + 1) > -1) {
		alert('email address must contain only one @');
		return false;
	}
	if (addr.indexOf('.', atPos) == -1) {
		alert('email address must contain a period in the domain name');
		return false;
	}
	if (addr.indexOf('@.', 0) != -1) {
		alert('period must not immediately follow @ in email address');
		return false;
	}
	if (addr.indexOf('.@', 0) != -1) {
		alert('period must not immediately precede @ in email address');
		return false;
	}
	if (addr.indexOf('..', 0) != -1) {
		alert('two periods must not be adjacent in email address');
		return false;
	}
	var suffix = addr.substring(addr.lastIndexOf('.') + 1);
	suffix = suffix.toLowerCase();
	if (suffix.length != 2 && suffix != 'com' && suffix != 'net'
			&& suffix != 'org' && suffix != 'edu' && suffix != 'int'
			&& suffix != 'mil' && suffix != 'gov' & suffix != 'arpa'
			&& suffix != 'biz' && suffix != 'aero' && suffix != 'name'
			&& suffix != 'coop' && suffix != 'info' && suffix != 'pro'
			&& suffix != 'museum') {
		alert('invalid primary domain in email address');
		return false;
	}
	return true;
}
// UAT fix
// Enter key navigation on only numeric text boxes
function onlyNumberNenterKeyNav(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}


// UAT fix
// Validate Time in HH:MM format
// hrOrMin is 'HH'/'MM'
function isValidTimeHHMM(obj, hrOrMin) {
	if (obj.value != null && obj.value != "") {
		if (hrOrMin == "HH") {
			var hour = obj.value;
			if (hour < 0 || hour > 23) {
				alert("Hour must be between 0 to 24");
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}
		} else if (hrOrMin == "MM") {
			var minute = obj.value;
			if (minute < 0 || minute > 59) {
				alert("Minute must be between 0 to 59.");
				obj.value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return false;
			}
		}
		return true;
	}

	function validateEmail(addr) {
		if (addr == '') {
			alert('email address is mandatory');
			return false;
		}

		var invalidChars = '\/\'\\ ";:?!()[]\{\}^|';
		for (i = 0; i < invalidChars.length; i++) {
			if (addr.indexOf(invalidChars.charAt(i), 0) > -1) {
				alert('email address contains invalid characters');
				return false;
			}
		}
		for (i = 0; i < addr.length; i++) {
			if (addr.charCodeAt(i) > 127) {
				alert("email address contains non ascii characters.");
				return false;
			}
		}

		var atPos = addr.indexOf('@', 0);
		if (atPos == -1) {
			alert('email address must contain an @');
			return false;
		}
		if (atPos == 0) {
			alert('email address must not start with @');
			return false;
		}
		if (addr.indexOf('@', atPos + 1) > -1) {
			alert('email address must contain only one @');
			return false;
		}
		if (addr.indexOf('.', atPos) == -1) {
			alert('email address must contain a period in the domain name');
			return false;
		}
		if (addr.indexOf('@.', 0) != -1) {
			alert('period must not immediately follow @ in email address');
			return false;
		}
		if (addr.indexOf('.@', 0) != -1) {
			alert('period must not immediately precede @ in email address');
			return false;
		}
		if (addr.indexOf('..', 0) != -1) {
			alert('two periods must not be adjacent in email address');
			return false;
		}
		var suffix = addr.substring(addr.lastIndexOf('.') + 1);
		suffix = suffix.toLowerCase();
		if (suffix.length != 2 && suffix != 'com' && suffix != 'net'
				&& suffix != 'org' && suffix != 'edu' && suffix != 'int'
				&& suffix != 'mil' && suffix != 'gov' & suffix != 'arpa'
				&& suffix != 'biz' && suffix != 'aero' && suffix != 'name'
				&& suffix != 'coop' && suffix != 'info' && suffix != 'pro'
				&& suffix != 'museum') {
			alert('invalid primary domain in email address');
			return false;
		}
		return true;
	}
}

/**
 * isEnterKey
 * 
 * @param evt
 * @returns {Boolean}
 * @author narmdr
 */
function isEnterKey(evt) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = evt.which; // firefox
	}

	if (charCode == 13) {
		return true;
	}
	return false;
}

/**
 * enterKeyNavigation
 * 
 * @param evt
 * @param elementId
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavigation(evt, elementId) {
	if (isEnterKey(evt)) {
		$("#" + elementId).click();
		return true;
	}
	return false;
}

/**
 * enterKeyNavFocusWithAlertIfEmpty
 * 
 * @param evt
 * @param elementIdToFocus
 * @param msgLabel
 * @returns {Boolean}
 * @author narmdr
 */
function enterKeyNavFocusWithAlertIfEmpty(evt, elementIdToFocus, msgLabel) {
	var pleaseSelectMsg = "Please Select ";
	var pleaseEnterMsg = "Please Enter ";
	var errorEndMsg = " !";

	var currentObj = getEventTargetJQObject(evt);
	if (isEnterKey(evt)) {
		if (!isNull($.trim(currentObj.val()))) {
			$("#" + elementIdToFocus).focus();
		} else {
			var msg = null;
			if (isDropDown(currentObj.attr("id"))) {
				msg = pleaseSelectMsg + msgLabel + errorEndMsg;
			} else {
				msg = pleaseEnterMsg + msgLabel + errorEndMsg;
			}
			clearFocusAlertMsg(currentObj, msg);
		}
		return true;
	}
	return false;
}

/**
 * isButton
 * 
 * @param fieldId
 * @returns {Boolean}
 * @author narmdr
 */
function isButton(fieldId) {
	if ($("#" + fieldId).is("input:button")) {
		return true;
	}
	return false;
}

/**
 * isTextBox
 * 
 * @param fieldId
 * @returns {Boolean}
 * @author narmdr
 */
function isTextBox(fieldId) {
	if ($("#" + fieldId).is("input:text")) {
		return true;
	}
	return false;
}

/**
 * isDropDown
 * 
 * @param fieldId
 * @returns {Boolean}
 * @author narmdr
 */
function isDropDown(fieldId) {
	if ($("#" + fieldId).is("select")) {
		return true;
	}
	return false;
}

/**
 * clearFocusAlertMsg
 * 
 * @param obj
 * @param msg
 * @author narmdr
 */
function clearFocusAlertMsg(obj, msg) {
	if (isJQueryObject(obj)) {
		obj.val("");
	} else {
		obj.value = "";
	}
	obj.focus();
	alert(msg);
	setTimeout(function() {
		obj.focus();
	}, 10);
}

/**
 * isJQueryObject
 * 
 * @param obj
 * @returns {Boolean}
 * @author narmdr
 */
function isJQueryObject(obj) {
	if (obj instanceof jQuery) {
		return true;
	}
	return false;
}

function addOptionTODropDown(selectId, label, value) {
	var myselect = document.getElementById(selectId);
	try {
		myselect.add(new Option(label, value), null); // add new option to end
		// of "myselect"
		// myselect.add(new Option(label, value), myselect.options[0]); //add
		// new option to beginning of "myselect"
	} catch (e) { // in IE, try the below version instead of add()
		myselect.add(new Option(label, value)); // add new option to end of
		// "myselect"
		// myselect.add(new Option(label, value), 0); //add new option to
		// beginning of "myselect"
	}
}

function setFocusToFieldIfEmptyById(elementId) {
	var elementObj = $("#" + elementId);
	if (isNull(elementObj.val())) {
		elementObj.focus();
	}
}
function showDropDownBySelected(domId, selectedVal) {
	var domElement = getDomElementById(domId);
	for ( var i = 0; i < domElement.options.length; i++) {
		if (domElement.options[i].value == selectedVal) {
			domElement.options[i].selected = 'selected';
		}
	}
}

// check NAN validation for decimals
function isNANCheckForDecimals(elementValue) {
	var elementValueStr = elementValue + "";
	for ( var i = 0; i < elementValueStr.length; i++) {
		var cn = elementValueStr.charAt(i);
		if (cn == ".") {
			continue;
		}
		if (isNaN(cn)) {
			return false;
		}
	}
	return true;
}

function getErrorMessage(obj) {
	var errorMsg = null;
	if (!isNull(obj)) {
		errorMsg = obj["ERROR"];
		// errorMsg = obj.ERROR;
	}
	return errorMsg;
}

function isText(value) {
	for (n = 0; n < value.length; n++) {
		if (!isDigit(value.charAt(n))) {
			return false;
		}
	}
	return true;
}

function isDigit(s) {
	if (s.length > 1) {
		return false;
	}
	var nums = '1234567890';
	return nums.indexOf(s) != -1;
}

function enableGlobalButton(btnId) {
	var glbBtn = $('#' + btnId);
	glbBtn.removeClass();
	glbBtn.addClass(BUTTON_ENABLE_CLASS);
	glbBtn.attr("disabled", false);
}
function disableGlobalButton(btnId) {
	var glbBtn = $('#' + btnId);
	glbBtn.removeClass();
	glbBtn.addClass(BUTTON_DISABLE_CLASS);
	glbBtn.attr("disabled", true);
}
function createDropDownForJquery(domId, jsonArrayList) {
	var domElement = getDomElementById(domId);
	domElement.options.length = 0;
	var optionSelectType = document.createElement("OPTION");
	optionSelectType.value = "";
	optionSelectType.text = "--Select--";
	try {
		domElement.add(optionSelectType, null);
	} catch (e) {
		domElement.add(optionSelectType);
	}

	for ( var i = 0; i < jsonArrayList.length; i++) {
		var objOption = document.createElement("option");
		objOption.value = trimString(jsonArrayList[i]['KEY']);
		objOption.text = trimString(jsonArrayList[i]['VALUE']);
		// modified for Browser compatibility(By sami)
		try {
			domElement.add(objOption, null);
		} catch (e) {
			domElement.add(objOption);
		}
	}
	// domElement.setAttribute("style","width: 100px");
	return domElement;
}

/**
 * checkUncheckAllRows
 * 
 * @param tableId
 * @param checkBoxFieldName
 * @author narmdr
 */
function checkUncheckAllRows(tableId, checkBoxFieldName) {
	var table = document.getElementById(tableId);
	var checkFlag = document.getElementById(checkBoxFieldName + "0").checked;

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id
				.substring(checkBoxFieldName.length);
		document.getElementById(checkBoxFieldName + rowId).checked = checkFlag;
	}
}

/**
 * clearWtGramIfEmpty
 * 
 * @param weightGmId
 * @author narmdr
 */
function clearWtGramIfEmpty(weightGmId) {
	var weightGmObj = $("#" + weightGmId);
	setTimeout(function() {
		if (weightGmObj.val() == "000") {
			weightGmObj.val("");
		}
	}, 10);
}

/**
 * @param obj
 *            Convert value to Upper Case
 */
function convertDOMObjValueToUpperCase(obj) {
	var domValue = $.trim(obj.value);
	obj.value = domValue.toUpperCase();
}

/**
 * getPressedKeyCode
 * 
 * @param evt
 * @returns
 * @author narmdr
 */
function getPressedKeyCode(evt) {
	var keyCode;
	if (window.event) {
		keyCode = window.event.keyCode; // IE
	} else {
		keyCode = evt.which; // firefox
	}
	return keyCode;
}

function focusById(elementId) {
	var elementObj = $("#" + elementId);
	// elementObj.focus();
	setTimeout(function() {
		elementObj.focus();
	}, 10);
}
/**
 * getEventTargetJQObject
 * 
 * @param event
 * @returns
 * 
 * @author narmdr
 */
function getEventTargetJQObject(event) {
	if (event.srcElement) {
		return $(event.srcElement);
	}
	return $(event.target);
	// event.srcElement in Internet Explorer
	// event.target in most other browsers.
}

/**
 * enterKeyNavigationFocus
 * 
 * @param evt
 * @param elementIdToFocus
 * @returns {Boolean}
 * 
 * @author narmdr
 */
function enterKeyNavigationFocus(evt, elementIdToFocus) {
	if (isEnterKey(evt)) {
		focusById(elementIdToFocus);
		return true;
	}
	return false;
}

/**
 * enterKeyNavCallOnChange
 * 
 * @param evt
 * @param elementId
 * 
 * @author narmdr
 */
function enterKeyNavCallOnChange(evt, elementId) {
	if (isEnterKey(evt)) {
		$("#" + elementId).trigger("change");
		// document.getElementById(elementId).onchange();
	}
}

/**
 * hideElementByDomId
 * 
 * @param domElmId
 */
function hideElementByDomId(domElmId) {
	// var targentElmt=getDomElementById(domElmId);
	var targentElmt = $('#' + domElmId);
	if (targentElmt != null) {
		// targentElmt.style.display ="none";
		targentElmt.hide();
	}

}

function setDataTableDefaultWidth() {
	$('.dataTables_scrollHeadInner').width("100%");
}

/**
 * To set maxlength for text area
 * @param e
 * @param obj
 * @param size
 * @returns {Boolean}
 */
function setMaxLengthForTextArea(e, obj, size) {
	var charCode;

	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode == 127 || charCode == 08 || charCode == 0) {
		return true;
	} else {
		if (obj.value.length > size) {
			return false;
		}
		return true;
	}

}
/**
 * checkStartsWith :: it checks whether sourceStr starts with text or not
 * @param text
 * @returns {Boolean}
 */
function checkStartsWith(sourceStr,text){
	 var result=false;
	 try{
	 var expression = new RegExp('^' + text);
	 result=expression.test(sourceStr);
	 }catch(e){
		 alert(e);
		 result=(sourceStr.indexOf(text) === 0);
	 }
	 return result;
}