/**
 * deleteTableRow
 * 
 * @param tableId
 * @author sdalli
 */

// These global variables are used in popup windows
var SELECTED_SERVICED_ON = null;
var SELECTED_CONSIGNMENT_TYPE = null;
var SELECTED_ROW_COUNT = null;
var isValidPincode=false;
function deleteTableRow(tableId) {
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				if (rowCount <= 2) {
					alert("Cannot delete all the rows.");
					break;
				}
				deleteRow(tableId, i - 1);
				// table.deleteRow(i);
				rowCount--;
				i--;
			}
		}
		// Updating serial no
		updateSrlNo(tableId);
	} catch (e) {
		alert(e);
	}
}

function deleteTableRowForParcel(tableId) {
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;

		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				if (rowCount <= 2) {
					alert("Cannot delete all the rows.");
					break;
				}
				var checkboxId = chkbox.id;
				var chkRow = checkboxId.split('chk');
				var childCNArr = new Array();

				var childCns = document.getElementById("childCns" + chkRow[1]).value;
				if (!isNull(childCns)) {
					var childCnsDtls = childCns.split("#");
					var childLength = childCnsDtls.length;
					for ( var m = 0; m < childLength; m++) {
						var childCn = childCnsDtls[m];
						var childCNNO = childCn.split(",")[0];
						childCNArr.push(childCNNO);

					}

					for ( var n = 0; n < childCNArr.length; n++) {
						for ( var p = 0; p < usedConsignments.length; p++) {
							if (!isNull(childCNArr[n])
									&& !isNull(usedConsignments[p])
									&& childCNArr[n] == usedConsignments[p]) {
								usedConsignments[p] = null;
							}
						}
					}

				}
				var consgNumber = document.getElementById("cnNumber"
						+ chkRow[1]).value;
				for ( var j = 0; j < usedConsignments.length; j++) {
					if (usedConsignments[j] == consgNumber) {
						usedConsignments[j] = null;
					}
				}
				deleteRow(tableId, i - 1);
				rowCount--;
				i--;
			}
		}
		updateSrlNo(tableId);
	} catch (e) {
		alert(e);
	}
}
/**
 * deleteRow
 * 
 * @param tableId
 * @param rowIndex
 * @author sdalli
 */
function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
}

/* Auto Getting Destination from Pincode */
/**
 * validatePincode
 * 
 * @param pinObj
 * @author sdalli
 */
function validatePincode(pinObj) {

	if (pinObj.value != null && pinObj.value != "") {
		rowNo = getRowId(pinObj, "cnPincode");
		var bookingOfficeId = "";
		var pincode = pinObj.value;
		var consgNumber = document.getElementById("cnNumber" + rowNo).value;
		var consgSeries = consgNumber.substring(4, 5);
		if (pincode.length != 6) {
			alert("Invalid pincode");
			isValidPincode=false;
			document.getElementById(pinObj.id).value = "";
			// document.getElementById(pinObj.id).focus();
			setTimeout(function() {
				document.getElementById(pinObj.id).focus();
			}, 10);
			return;
		}

		bookingOfficeId = document.getElementById("bookingOfficeId").value;
		// showProcessing();
		url = './creditCustomerBookingParcel.do?submitName=validatePincode&pincode='
				+ pincode
				+ "&bookingOfficeId="
				+ bookingOfficeId
				+ "&consgSeries=" + consgSeries;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincode(req, rowNo);
			}
		});
	}

}

function calPriorityRate() {
	var value = "popup";
	if (SELECTED_CONSIGNMENT_TYPE == "DOX") {

		getCNRateDtls(SELECTED_ROW_COUNT, value);
	} else {
		getCNRateDtlsParcel(SELECTED_ROW_COUNT, value);
	}
}

/**
 * printCallBackPincode
 * 
 * @param data
 * @param rowNo
 * @author sdalli
 */
function printCallBackPincode(data, rowNo) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isValidPincode == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("cnPincode" + rowNo).value = "";
				document.getElementById("cnPincode" + rowNo).focus();
				isValidPincode=false;
				return false;
			} else if (cnValidation.isValidPriorityPincode == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("cnPincode" + rowNo).value = "";
				document.getElementById("cnPincode" + rowNo).focus();
				isValidPincode=false;
				return false;
			} else {
				document.getElementById("destCities" + rowNo).value = cnValidation.cityName;
				document.getElementById("cityIds" + rowNo).value = cnValidation.cityId;
				document.getElementById("pinIds" + rowNo).value = cnValidation.pincodeId;
				isValidPincode=true;
			}
		}
		var cnNumber = document.getElementById('cnNumber' + rowNo).value;
		var cnValue = cnNumber.charAt(4);

		if (cnValue == "p" || cnValue == "P" || cnValue == "t"
				|| cnValue == "T")
		// if(cnValue=="p" || cnValue=="P")
		{
			url = "./consignerConsignee.do?rowNo=" + rowNo;
			jQuery.blockUI();
			
			// Following code is written to keep child window in centre of the screen
			var leftPosition, topPosition, width, height;
			width = 1000;
			height = 400;
			leftPosition = (window.screen.width / 2) - ((width / 2));
		    topPosition = (window.screen.height / 2) - ((height / 2));
		    
			var w = window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width='+ width +',height='+ height +',left = ' + leftPosition + ',top = '+ topPosition + ',ScreenX=' + leftPosition + ',ScreenY='+ topPosition);
			
			/*var w = window
					.open(
							url,
							'newWindow',
							'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width='+ width +',height= '+ height +',left = 100,top = 184');*/

			var watchClose = setInterval(function() {
				try {
					if (w.closed) {
						jQuery.unblockUI();
						clearTimeout(watchClose);
						calPriorityRate();
					}
				} catch (e) {
				}
			}, 200);
		}

	}
	// jQuery.unblockUI();
}

/**
 * validateConsignmentCCBookingParcel
 * 
 * @param consgNumberObj
 * @author sdalli
 */
function validateConsignmentCCBookingParcel(consgNumberObj) {
	if(consgNumberObj.readOnly==false){
	if (isDuplicateConsignment(consgNumberObj)) {
		alert("CN Number is already in Use.");
		document.getElementById(consgNumberObj.id).value = "";
		setTimeout(function() {
			document.getElementById(consgNumberObj.id).focus();
		}, 10);
		return;
	} else {
		validateConsignmentCreditParcel(consgNumberObj);
	}
	}
}
/**
 * validateConsignmentCreditParcel
 * 
 * @param consgNumberObj
 * @author sdalli
 */
function validateConsignmentCreditParcel(consgNumberObj) {
	consgNumberObj.value = $.trim(consgNumberObj.value);
	var bookingType = document.getElementById("bookingType").value;
	var rowCount = getRowId(consgNumberObj, "cnNumber");
	if (consgNumberObj.value != null && consgNumberObj.value != ""
			&& !consgNumberObj.readOnly) {
		var consgNumber = consgNumberObj.value;
		if (consgNumber.length != 12) {
			alert("Consingment length should be 12 character");
			document.getElementById(consgNumberObj.id).value = "";
			setTimeout(function() {
				document.getElementById(consgNumberObj.id).focus();
			}, 10);
			return;
		}else{
			var numpattern = /^[0-9]{3,20}$/;
			var letters = /^[A-Za-z]+$/;
			if (!numpattern.test(consgNumberObj.value.substring(5))
					|| !letters.test(consgNumberObj.value.substring(0, 1))) {

				alert('Consignment number format is not correct');
				consgNumberObj.value = "";
				setTimeout(function() {
					consgNumberObj.focus();
				}, 10);
				return;
			}

		}
		showProcessing();
		var custId = document.getElementById("customerId").value;
		url = './creditCustomerBookingParcel.do?submitName=validateConsignment&consgNumber='
				+ consgNumber
				+ '&rowCount='
				+ rowCount
				+ "&bookingType="
				+ bookingType;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignment(req);
			}
		});

	}

}

/**
 * printCallBackConsignment
 * 
 * @param data
 * @author sdalli
 */
function printCallBackConsignment(data) {

	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		var rowCount = cnValidation.rowCount;
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isValidCN == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("cnNumber" + rowCount).value = "";
				setTimeout(function() {
					document.getElementById("cnNumber" + rowCount).focus();
				}, 10);
			} else if (cnValidation.isConsgExists == "Y") {
				alert(cnValidation.errorMsg);
				document.getElementById("cnNumber" + rowCount).value = "";
				setTimeout(function() {
					document.getElementById("cnNumber" + rowCount).focus();
				}, 10);
			} else {
				if (!isNull(cnValidation.bookingId))
					document.getElementById("bookingIds" + rowCount).value = cnValidation.bookingId;
				if (!isNull(cnValidation.pickupRunsheetNo))
					document.getElementById("pickupRunsheetNos" + rowCount).value = cnValidation.pickupRunsheetNo;
				if (!isNull(cnValidation.custID)) {
					document.getElementById("custCode" + rowCount).value = cnValidation.custCode;
					document.getElementById("customerIds" + rowCount).value = cnValidation.custID;
					var customerId = document.getElementById("customerId").value;
					if (isNull(customerId)) {
						document.getElementById("customerId").value = cnValidation.custID;
					}
					
				}
				document.getElementById("cnNumber" + rowCount).readOnly = true;
				var consgNumber = document
						.getElementById("cnNumber" + rowCount).value;
				enableDisableCodAndLCAmt(consgNumber, rowCount);
				validateWeightFromWMParcel(rowCount);
			}
		}
	}
	jQuery.unblockUI();
}

/**
 * convertToFractionAW
 * 
 * @param obj
 * @param digits
 * @author sdalli
 */
function convertToFractionAW(obj, digits) {
	rowNo = getRowId(obj, "weightGmAW");
	var gmValue = document.getElementById("weightGmAW" + rowNo).value;
	var kgValue = document.getElementById("weightAW" + rowNo).value;

	var finalWeight = parseFloat(kgValue + "." + gmValue);
	

	/*if (gmValue.length > 0) {
		gmValue = parseFloat(gmValue);
	}
	if (kgValue.length > 0) {
		kgValue = parseFloat(kgValue);
	}*/
	if (!isNull(gmValue)) {
		if (gmValue.length == 0) {
			document.getElementById("weightGmAW" + rowNo).value = "000";
			gmValue += "000";
		} else if (gmValue.length == 1) {
			document.getElementById("weightGmAW" + rowNo).value += "00";
			gmValue += "00";
		} else if (gmValue.length == 2) {
			document.getElementById("weightGmAW" + rowNo).value += "0";
			gmValue += "0";
		}
	} else {

		document.getElementById("weightGmAW" + rowNo).value = "000";
		gmValue = "000";
	}
	if (!isNull(kgValue)) {
		if (kgValue.length == 0) {
			document.getElementById("weightAW" + rowNo).value += "0";
			kgValue += "0";
		}
	} else {
		document.getElementById("weightAW" + rowNo).value = "0";
		// kgValue += "0";
		kgValue = "0";
	}
	document.getElementById("cnActualWeight" + rowNo).value = finalWeight;

}

/**
 * convertToFractionCW
 * 
 * @param obj
 * @param digits
 * @author sdalli
 */
function convertToFractionCW(obj, digits) {
	rowNo = getRowId(obj, "contentCode");
	// if(!isNull(obj.value)){
	// var val=parseFloat(obj.value).toFixed(digits);
	// document.getElementById("weightGmCW"+rowNo).value=val;

	kgValue = document.getElementById("weightCW" + rowNo).value;
	gmValue = document.getElementById("weightGmCW" + rowNo).value;

	var finalWeight = parseFloat(kgValue + "." + gmValue).toFixed(3);
	document.getElementById("cnChrgWeight" + rowNo).value = finalWeight;

}

/**
 * convertToFractionVW
 * 
 * @param obj
 * @param digits
 * @author sdalli
 */
function convertToFractionVW(obj, digits) {
	rowNo = getRowId(obj, "volWeight");
	if (!isNull(obj.value)) {
		var val = parseFloat(obj.value).toFixed(digits);
		document.getElementById("volWeight" + rowNo).value = val;
	} else {
		document.getElementById("volWeight" + rowNo).value = "";
	}
}

/**
 * redirectPagecredit
 * 
 * @author sdalli
 */
function redirectPagecredit() {

	var docTypeIdName = document.getElementById('consgTypeName').value;
	var docType = "";
	if (docTypeIdName != null)
		docType = docTypeIdName.split("#")[1];
	if (docType == "PPX") {
		url = "./creditCustomerBookingParcel.do?submitName=viewCreditCustomerBookingParcel&docType="
				+ docType;
		window.location = url;
	} else if (docType == "DOX") {
		url = './creditCustomerBookingDox.do?submitName=viewCreditCustomerBookingDox';
		window.location = url;
	}
}

/**
 * validateCustCode
 * 
 * @param obj
 * @author sdalli
 */
function validateCustCode(obj) {

	var rowNo = getRowId(obj, "custCode");
	var custCode = document.getElementById('custCode' + rowNo).value;
	if (custCode.length != 10) {
		alert("Customer Code Should be 10 digits.");
		document.getElementById("custCode" + rowNo).value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return;
	}

	ROW_COUNT = rowNo;
	url = './creditCustomerBookingParcel.do?submitName=validateCustCode&custCode='
			+ custCode;
	ajaxCallWithoutForm(url, validateCustCodeResponse);
}

/**
 * validateCustCodeResponse
 * 
 * @param data
 * @author sdalli
 */
function validateCustCodeResponse(data) {
	var custDetails = data;
	if (custDetails.isCustValid == "N") {
		alert('Customer code not valid');
		document.getElementById("custCode" + ROW_COUNT).value = "";
		setTimeout(function() {
			document.getElementById("custCode" + ROW_COUNT).focus();
		}, 10);
		return;
	} else if (custDetails.isCustValid == "Y") {
		document.getElementById('customerIds' + ROW_COUNT).value = custDetails.custID;
		document.getElementById("custCode" + ROW_COUNT).value = custDetails.custCode;
	}

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
 * capturedWeight
 * 
 * @param data
 * @author sdalli
 */
function capturedWeight(data) {
	var capturedWeight = eval('(' + data + ')');
	if (capturedWeight == -1 || capturedWeight == -2) {
		isWeighingMachineConnected = false;
	} else {
		wmWeightActual = parseFloat(capturedWeight).toFixed(3);
		isWeighingMachineConnected = true;
	}
}

/**
 * validateWeightFromWeighingMachine
 * 
 * @param rowId
 * @author sdalli
 */
function validateWeightFromWMParcel(rowId) {
	if (!isNull(document.getElementById("cnNumber" + rowId).value)) {
		if (isWeighingMachineConnected) {
			getWeightFromWeighingMachineWithParam(capturedWeightInGridParcel,
					rowId);
		}
		disableEnableWeightField(rowId, isWeighingMachineConnected);
	}
}

/**
 * validateWeightFromWeighingMachine
 * 
 * @param rowId
 * @author sdalli
 */
function validateWeightFromWM(rowId) {
	if (!isNull(document.getElementById("cnNumber" + rowId).value)) {
		if (isWeighingMachineConnected) {
			getWeightFromWeighingMachineWithParam(capturedWeightInGrid, rowId);
		}
		disableEnableWeightField(rowId, isWeighingMachineConnected);
	}
}

/**
 * Enter key navigation
 * 
 * @param e
 * @param rowCount
 * @param contentCode
 * @returns {Boolean}
 */
function callEnterKeyInContentCredit(e, rowCount, contentCode) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		if (!isNull(contentCode)) {
			var value = contentCode.split("#")[1];
			if (!isNull(value) && value == '999') {
				document.getElementById("cnContentOther" + rowCount).focus();
			} else {
				document.getElementById("declaredValue" + rowCount).focus();
			}
		}
	}
	return false;
}

function setCnFocus() {
	document.getElementById("cnNumber1").focus();
}

/**
 * 
 * @param consgNumber
 * @param rowCount
 */
function enableDisableCodAndLCAmt(consgNumber, rowCount) {
	var consgSeries = consgNumber.substring(4, 5).toUpperCase();
	if (consgSeries == "L" || consgSeries == "T" || consgSeries == "D") {
		document.getElementById("codOrLcAmts" + rowCount).disabled = false;
	} else {
		document.getElementById("codOrLcAmts" + rowCount).disabled = true;
		document.getElementById("codOrLcAmts" + rowCount).tabindex = -1;
	}
	if (consgSeries == "D") {
		document.getElementById("lcBankNames" + rowCount).disabled = false;
	} else {
		document.getElementById("lcBankNames" + rowCount).disabled = true;
		document.getElementById("lcBankNames" + rowCount).tabindex = -1;
	}
}

/**
 * 
 * @param rowIds
 * @returns {Boolean}
 */
function validateConsigneeConsignorDetails(rowIds) {
	var isValid = true;
	var cnPincode = document.getElementById("cnPincode" + rowIds).value;
	var cnrPopDtls = document.getElementById("cnrdetails" + rowIds).value;
	var cnePopDtls = document.getElementById("cnedetails" + rowIds).value;
	var cnNumber = document.getElementById('cnNumber' + rowIds).value;
	var cnValue = cnNumber.charAt(4);

	if (cnValue == "p" || cnValue == "P" || cnValue == "t" || cnValue == "T") {
		if (!isNull(cnPincode)) {
			if (isNull(cnrPopDtls)) {
				alert("Please Enter consignor Details in PopUp  At Row: "
						+ rowIds);
				setTimeout(function() {
					document.getElementById("cnPincode" + rowIds).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			if (isNull(cnePopDtls)) {
				alert("Please Enter ConsigneeDetails in PopUp At Row: "
						+ rowIds);
				setTimeout(function() {
					document.getElementById("cnPincode" + rowIds).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
		}
	}
	return isValid;
}


/**
 * 
 * @param pageurl
 * @param ajaxResponse
 */
function ajaxCallWithoutFormAsyncRequest(pageurl, ajaxResponse) {
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		async : false,
		success : function(data) {
			ajaxResponse(data);
		}
	});

}
