/*Function to get ID from object*/
/**
 * Obj
 * 
 * @param id
 * @returns
 * @author sdalli
 */
function Obj(id) {
	return document.getElementById(id);
}
/* Payment Mode for drop down */
/**
 * showPane
 * 
 * @author sdalli
 */
function showPane() {
	var paymentMode = document.getElementById('payMode').value;
	var payMode = paymentMode.split("#")[1];
	if (payMode == 'CHQ')// Cheque
	{
		Obj("tblcheque").style.display = "";
		Obj("tblbankbranch").style.display = "";
		Obj("tblprivcard").style.display = "none";
		document.getElementById('privilegeCardAmt').value = "";
		document.getElementById('privilegeCardNo').value = "";
		document.getElementById('privilegeCardId').value = "";

	} else if (payMode == 'PVC')// Credit Card
	{
		Obj("tblcheque").style.display = "none";
		Obj("tblbankbranch").style.display = "none";
		Obj("tblprivcard").style.display = "";
		document.getElementById('chequeDate').value = "";
		document.getElementById('cno').value = "";
		document.getElementById('bankBranchName').value = "";
		document.getElementById('bank').value = "";
	} else if (payMode == 'CA')// Cash
	{

		Obj("tblcheque").style.display = "none";
		Obj("tblbankbranch").style.display = "none";
		Obj("tblprivcard").style.display = "none";
		document.getElementById('chequeDate').value = "";
		document.getElementById('cno').value = "";
		document.getElementById('bankBranchName').value = "";
		document.getElementById('bank').value = "";
		document.getElementById('privilegeCardAmt').value = "";
		document.getElementById('privilegeCardNo').value = "";
		document.getElementById('privilegeCardId').value = "";
	}
}
$(document).ready(function() {
	if (isNull(getValueByElementId("requestHeaderId"))) {
		fnClickAddRow();
	}
});

/* Cheking Mobile No# should be 10 digits. */
/**
 * isValidMobile
 * 
 * @param obj
 * @param fieldId
 * @returns
 * @author sdalli
 */
function isValidMobile(obj, fieldId) {
	var mobile = obj.value;
	if (!isNull(mobile)) {
		if (mobile.length != 10) {
			alert("Enter 10 Digits Mobile Number");
			setTimeout(function() {
				obj.focus();
			}, 10);
			isValid = false;
			return isValid;

		}
	}
}

/* Cheking Phone No# should be 11 digits. */
/**
 * isValidPhoneBooking
 * 
 * @param obj
 * @param fieldId
 * @returns
 * @author sdalli
 */
function isValidPhoneBooking(obj, fieldId) {
	var phone = obj.value;
	if (!isNull(phone)) {
		if (phone.length != 11) {
			alert("Enter 11 Digits Phone Number ");
			setTimeout(function() {
				obj.focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
}

function isValidAddress(obj, fieldId) {
	var address = obj.value;
	if (!isNull(address)) {
		if (address.length > 1000) {
			alert("Please Enter  address of valid length");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
}
/**
 * isUserName
 * 
 * @param userName
 * @returns {Boolean}
 * @author sdalli
 */
function isUserName(userName) {
	var nameRegex = /^[a-zA-Z\-]+$/;
	var isvalidUsername = userName.match(nameRegex);
	if (isvalidUsername == null) {
		alert("Your first name is not valid. Only characters A-Z, a-z and '-' are  acceptable.");
		return false;
	}
}

/* Adding additional digits to Gm up to 3 digits */
/**
 * weightFormatForGm
 * 
 * @author sdalli
 */
function weightFormatForGm() {
	var gmValue = document.getElementById("weightGm").value;
	var kgValue = document.getElementById("weight").value;

	var finalWeight = parseFloat(kgValue + "." + gmValue);
	

	if (gmValue.length > 0) {
		gmValue = parseFloat(gmValue);
	}
	if (kgValue.length > 0) {
		kgValue = parseFloat(kgValue);
	}
	if (gmValue.length == 0) {
		document.getElementById("weightGm").value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("weightGm").value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("weightGm").value += "0";
		gmValue += "0";
	} 
	
	if (kgValue.length == 0) {
		document.getElementById("weight").value += "0";
		kgValue += "0";
	}  
	
	document.getElementById("finalWeight").value = finalWeight;
	
	
/*	var gmValue = document.getElementById("weightGm").value;
	if (gmValue.length == 0) {
		document.getElementById("weightGm").value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("weightGm").value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("weightGm").value += "0";
		gmValue += "0";
	}

	 Adding additional decimal value to Kg up to 1 decimal 
	var kgValue = document.getElementById("weight").value;
	if (kgValue.length == 0) {
		document.getElementById("weight").value += "0";
		kgValue += "0";
	}
	document.getElementById("finalWeight").value = (kgValue + "." + gmValue);*/
	
}

/**
 * onlyNumberAndEnterKeyNav
 * 
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 * @author sdalli
 */
function onlyNumberAndEnterKeyNav(e, Obj, focusId) {
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

/**
 * onlyDecimalAndEnterKeyNav
 * 
 * @param e
 * @param Obj
 * @param focusId
 * @returns {Boolean}
 * @author sdalli
 */
function onlyDecimalAndEnterKeyNav(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode != 46) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}

/* Enter key navigation */
function callEnterKeyInRefNo(e, rowCount) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var nextRow = parseInt(rowCount) + 1;
		if (eval(document.getElementById("cnNumber" + nextRow)) != null
				&& eval(document.getElementById("cnNumber" + nextRow)) != "") {
			document.getElementById("cnNumber" + nextRow).focus();
		}
		return false;
	}
}

/* Enter key navigation */
function callEnterKeyInContent(e, rowCount, contentCode) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		if (contentCode == 'O') {
			document.getElementById("cnContentOther" + rowCount).focus();
		} else {
			document.getElementById("declaredValue" + rowCount).focus();
		}
		return false;
	}
}

function getInsuredByDtls(rowCount) {
	var url = './cashBookingParcel.do?submitName=getInsuredDtls';
	jQuery.ajax({
		url : url,
		type : "POST",
		success : function(req) {
			papulateInsuredByDtls(req, rowCount);
		}
	});
}

function papulateInsuredByDtls(data, rowCount) {
	var insuredBy = "";
	var option;
	if (rowCount > 0)
		insuredBy = document.getElementById('insuaranceNo' + rowCount);
	else
		insuredBy = document.getElementById("insuaranceNo");
	insuredBy.innerHTML = "";

	var insuredDetails = eval('(' + data + ')');
	if (insuredDetails != null && insuredDetails != "") {
		var noOfInsuredBy = insuredDetails.length;
		for (i = 0; i < noOfInsuredBy; i++) {
			/*
			 * option = document.createElement("option"); option.value =
			 * insuredDetails[i].insuredById; option.description =
			 * insuredDetails[i].insuredByDesc; insuredBy.appendChild(option);
			 */
			addOptionTODropDown(insuredBy.id, insuredDetails[i].insuredByDesc,
					insuredDetails[i].insuredById);
		}
	}
}

function validateParentAndChildCnsWeight(parentCnWeight, chilsCNDtls, rowCount) {
	if (!isNull(chilsCNDtls)) {
		var consgWeight = parseFloat(parentCnWeight);
		var childCns = new Array();
		var childCnsTotalWeight = 0.00;
		childCns = chilsCNDtls.split("#");
		for ( var i = 0; i < childCns.length; i++) {
			var childCn = childCns[i];
			var childCNWeight = childCn.split(",")[1];
			childCnsTotalWeight = childCnsTotalWeight
					+ parseFloat(childCNWeight);
		}
		if(!isEmptyWeight(consgWeight) &&  !isNaN(consgWeight) && !isEmptyWeight(childCnsTotalWeight) && !isNaN(childCnsTotalWeight)){
		if (parseFloat(consgWeight) < parseFloat(childCnsTotalWeight)) {
			childCnsTotalWeight=parseFloat(childCnsTotalWeight).toFixed(3);
			alert("Parent consignment weight can't be less than total of child consignments weight.("
					+ childCnsTotalWeight + ")");
			if (rowCount > 0) {
				document.getElementById('weightAW' + rowCount).value = "";
				document.getElementById('weightGmAW' + rowCount).value = "";
				document.getElementById('weightCW' + rowCount).value = "";
				document.getElementById('weightGmCW' + rowCount).value = "";
				setTimeout(function() {
					document.getElementById("weightAW" + rowCount).focus();
				}, 10);
			} else {
				document.getElementById('weight').value = "";
				document.getElementById('weightGm').value = "";
				document.getElementById('weight').focus();

			}
		}
	}
	}
}

function OnlyAlphabetsAndNosAndEnter(obj, event, elementIdToFocus, msgLabel) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = event.which; // firefox
	// alert("charCode"+charCode);
	if ((charCode >= 97 && charCode <= 122)
			|| (charCode >= 65 && charCode <= 90) || charCode == 9
			|| charCode == 8 || (charCode >= 48 && charCode <= 57)
			|| charCode == 0) {

		return true;

	} else if (charCode == 13) {
		enterKeyNavFocusWithAlertIfEmptyBooking(obj, event, elementIdToFocus,
				msgLabel);
	} else {
		return false;
	}
	return false;
}

function enterKeyNavFocusWithAlertIfEmptyBooking(obj, evt, elementIdToFocus,
		msgLabel) {
	var pleaseSelectMsg = "Please Select ";
	var pleaseEnterMsg = "Please Enter ";
	var errorEndMsg = " !";

	var currentObj = $(evt.target);
	if (isEnterKey(evt)) {
		if (!isNull($.trim(obj.value))) {
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
function onlyDecimalNenterKeyNav(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode != 46) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}