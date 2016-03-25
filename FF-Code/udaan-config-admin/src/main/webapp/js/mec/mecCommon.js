/**
 * To validate cheque number
 * 
 * @param chqNo
 * @returns {Boolean}
 */
function validateChqNumber(chqNo) {
	var isValidReturnVal = true;
	if (!isNull(chqNo.value)) {
		if (chqNo.value.length < 6 || chqNo.value.length > 9) {
			alert('Cheque No. length should be 6 to 9 character');
			chqNo.value = "";
			setTimeout(function() {
				chqNo.focus();
			}, 10);
			isValidReturnVal = false;
		}

	}
	return isValidReturnVal;
}

/**
 * To check whether amount is empty or not
 * 
 * @param amt
 * @returns {Boolean}
 */
function isEmptyAmount(amt) {
	var dotPattern = /^[.]{2,10}$/;
	if (isNull(amt) || isNull(parseFloat(amt)) || amt == "."
			|| amt.match(dotPattern)) {
		return true;
	}
	return false;
}

/**
 * To show processing image
 */
function showProcessing() {
	jQuery.blockUI({
		message : '<img src="images/loading_animation.gif"/>'
	});
}

/**
 * To hide processing image
 */
function hideProcessing() {
	jQuery.unblockUI();
}

/**
 * To clear drop down list
 * 
 * @param selectId
 */
function clearDropDownList(selectId) {
	getDomElementById(selectId).options.length = 0;
	addOptionTODropDown(selectId, selectOption, "");
}

/**
 * It adds the values to dropdown
 * 
 * @param selectId
 * @param label
 * @param value
 */
function addOptionTODropDown(selectId, label, value) {
	var obj = getDomElementById(selectId);
	opt = document.createElement('OPTION');
	opt.value = value;
	opt.text = label;
	obj.options.add(opt);
}

/**
 * Enter Key navigation with allowing only decimal
 * 
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 */
function onlyNumberAndEnterKeyNav(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode != 46) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}

/**
 * To set amount format and set zero if NULL
 * 
 * @param domElement
 */
function setAmountFormatZero(domElement) {
	if (domElement.readOnly == false && !isEmptyAmount(domElement.value)) {
		var domVal = domElement.value;
		var amtValue = domVal.split(".");
		if (isNull(amtValue[0])) {
			amtValue[0] = "0";
		}
		if (isNull(amtValue[1])) {
			amtValue[1] = "00";
		} else if (amtValue[1].length == 1) {
			amtValue[1] = amtValue[1] + "0";
		} else if (amtValue[1].length > 2) {
			amtValue[1] = amtValue[1].substring(0, 2);
		}
		domElement.value = amtValue[0] + "." + amtValue[1];
	} else if (isEmptyAmount(domElement.value)){
		domElement.value = "0.00";
	}
}

/**
 * To set amount format and set zero if NULL for consingment expense only
 * 
 * @param domElement
 */
function setCNAmtFormatZero(domElement) {
	if (!isEmptyAmount(domElement.value)) {
		var domVal = domElement.value;
		var amtValue = domVal.split(".");
		if (isNull(amtValue[0])) {
			amtValue[0] = "0";
		}
		if (isNull(amtValue[1])) {
			amtValue[1] = "00";
		} else if (amtValue[1].length == 1) {
			amtValue[1] = amtValue[1] + "0";
		} else if (amtValue[1].length > 2) {
			amtValue[1] = amtValue[1].substring(0, 2);
		}
		domElement.value = amtValue[0] + "." + amtValue[1];
	} else {
		domElement.value = "0.00";
	}
}

/**
 * To call function to avoid special character(s) - Allow only alpha numeric
 * 
 * @param e
 * @param focusObj
 * @returns
 */
function callEnterKeyAlphaNum(e, focusObj) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = e.which; // firefox
		/*
		 * if (isNull(e.which) && !isNull(e.keyCode)) { charCode = e.keyCode; }
		 */
	}
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90)
			|| (charCode >= 48 && charCode <= 57) || charCode == 8
			|| charCode == 9 || charCode == 0) {
		flag = true;
	} else if (charCode == 13) {
		setTimeout(function() {
			focusObj.focus();
		}, 10);
		flag = false;
	} else {
		flag = false;
	}
	return flag;
}

/**
 * To set amount format i.e. 12345.67 Its NEW amount format dicided in UAT MAY
 * 2014 22_05_2014
 * 
 * @param domElement
 */
function setNewAmtFormat(domElement) {
	if (!isEmptyAmount(domElement.value)) {
		var domVal = domElement.value;
		var amtValue = domVal.split(".");
		if (isNull(amtValue[0])) {
			amtValue[0] = "0";
		}
		if (isNull(amtValue[1])) {
			amtValue[1] = "00";
		} else if (amtValue[1].length == 1) {
			amtValue[1] = amtValue[1] + "0";
		} else if (amtValue[1].length > 2) {
			amtValue[1] = amtValue[1].substring(0, 2);
		}
		domElement.value = amtValue[0] + "." + amtValue[1];
		// To check whether amount length is proper or not
		if (!isAmountValidLength(amtValue[0], 5)) {
			alert("Please provide valid amount (i.e 12345.67)");
			domElement.value = "";
			setTimeout(function() {
				domElement.focus();
			}, 10);
			return false;
		}
	} else {
		domElement.value = "";
	}
}

/**
 * To check whether it is amount format is proper or not i.e. 5-digit with 2
 * decimal (12345.67)
 * 
 * @param amt
 * @param len
 */
function isAmountValidLength(amt, len) {
	var amtStr = amt + " ";
	amtStr = trimString(amtStr);
	if (amtStr.length > len) {
		return false;
	}
	return true;
}

/**
 * To NEW set amount format and set zero if NULL i.e. 12345.67 Its NEW amount
 * format dicided in UAT MAY 2014 22_05_2014
 * 
 * @param domElement
 */
function setNewAmtFormatZero(domElement) {
	if (!isEmptyAmount(domElement.value)) {
		var domVal = domElement.value;
		var amtValue = domVal.split(".");
		if (isNull(amtValue[0])) {
			amtValue[0] = "0";
		}
		if (isNull(amtValue[1])) {
			amtValue[1] = "00";
		} else if (amtValue[1].length == 1) {
			amtValue[1] = amtValue[1] + "0";
		} else if (amtValue[1].length > 2) {
			amtValue[1] = amtValue[1].substring(0, 2);
		}
		domElement.value = amtValue[0] + "." + amtValue[1];
		// To check whether amount length is proper or not
		if (!isAmountValidLength(amtValue[0], 5)) {
			alert("Please provide valid amount (i.e 12345.67)");
			domElement.value = "";
			setTimeout(function() {
				domElement.focus();
			}, 10);
			return false;
		}
	} else {
		domElement.value = "0.00";
	}
}

function isEmptyZeroAmount(domObj) {
	if (isNull(domObj.value) || isNull(parseFloat(domObj.value)) || domObj.value == "0.00" || domObj.value == "0.0") {
		alert("Please provide Amount");
		domObj.value = '';
		setTimeout(function() {
			domObj.focus();
		}, 10);
		return false;
	}
	return true;
}
