/**
 * deleteTableRow
 * 
 * @param tableId
 * @author sdalli
 */

// Global variables used in consigner popup
var SELECTED_SERVICED_ON = null;
var SELECTED_CONSIGNMENT_TYPE = null;
var SELECTED_ROW_COUNT = null;
var isValidPincode = false;
function deleteTableRow(tableId) {
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;
		
		var isDel = false;
		var isFirstRow=false;
		for ( var i = 0; i < rowCount; i++) {
			var row = table.rows[i];
			var chkbox = row.cells[0].childNodes[0];
			if (null != chkbox && true == chkbox.checked) {
				if (rowCount <= 2) {
					alert("Cannot delete all the rows.");
					isFirstRow=true;
					break;
				}
				var checkboxId = chkbox.id;
				var chkRow = checkboxId.split('chk');

				var consgNumber = document.getElementById("cnNumber"
						+ chkRow[1]).value;
				for ( var j = 0; j < usedConsignments.length; j++) {
					if (usedConsignments[j] == consgNumber) {
						usedConsignments[j] = null;
					}
				}

				isDel = deleteRow(tableId, i - 1);
				rowCount--;
				i--;
			}
		}
		updateSrlNo(tableId);
		if(!isFirstRow){
		if(isDel)
			alert("Row(s) deleted successfully.");
		else
			alert("Please select atleast one row to delete.");
		
		}
		
	} catch (e) {
		alert(e);
	}
}

function deleteTableRowForParcel(tableId) {
	try {
		var table = document.getElementById(tableId);
		var rowCount = table.rows.length;
		var isDel = false;
		
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
				isDel = deleteRow(tableId, i - 1);
				rowCount--;
				i--;
			}
		}
		
		if(isDel)
			alert("Row(s) deleted successfully.");
		else
			alert("Please select atleast one row to delete.");
		
		updateSrlNo(tableId);
	} catch (e) {
		alert(e);
	}
}

function deleteRow(tableId, rowIndex) {
	var oTable = $('#' + tableId).dataTable();
	oTable.fnDeleteRow(rowIndex);
	
	return true;
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
			isValidPincode = false;
			document.getElementById(pinObj.id).value = "";
			// document.getElementById(pinObj.id).focus();
			setTimeout(function() {
				document.getElementById(pinObj.id).focus();
			}, 10);
			return;
		}

		bookingOfficeId = document.getElementById("bookingOfficeId").value;
		// showProcessing();
		url = './baBookingDox.do?submitName=validatePincode&pincode=' + pincode
				+ "&bookingOfficeId=" + bookingOfficeId + "&consgSeries="
				+ consgSeries;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincode(req, rowNo);
			}
		});
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
				isValidPincode = false;
			} else if (cnValidation.isValidPriorityPincode == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("cnPincode" + rowNo).value = "";
				document.getElementById("cnPincode" + rowNo).focus();
				isValidPincode = false;
			} else {
				document.getElementById("destCities" + rowNo).value = cnValidation.cityName;
				document.getElementById("cityIds" + rowNo).value = cnValidation.cityId;
				document.getElementById("pinIds" + rowNo).value = cnValidation.pincodeId;
				isValidPincode = true;
				var cnNumber = document.getElementById('cnNumber' + rowNo).value;
				var cnValue = cnNumber.charAt(4);

				// if(cnValue=="p" || cnValue=="P" || cnValue=="t" ||
				// cnValue=="T")
				if (cnValue == "p" || cnValue == "P" || cnValue == "t"
						|| cnValue == "T") {
					url = "./consignerConsignee.do?rowNo=" + rowNo;
					jQuery.blockUI();
					var w = window
							.open(
									url,
									'newWindow',
									'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=1000,height=400,left = 412,top = 184');

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
		}
	}
	// jQuery.unblockUI();
}

/**
 * validateConsignment
 * 
 * @param consgNumberObj
 * @author sdalli
 */
function validateConsignment(consgNumberObj) {
	consgNumberObj.value = $.trim(consgNumberObj.value);
	var consgNumber = consgNumberObj.value;
	var rowCount = getRowId(consgNumberObj, "cnNumber");
	var baId = document.getElementById("baId").value;
	var bookingType = document.getElementById("bookingType").value;
	if (consgNumberObj.value != null && consgNumberObj.value != ""
			&& !consgNumberObj.readOnly) {
		if (consgNumber.length != 12) {
			alert("Consingment length should be 12 character");
			document.getElementById(consgNumberObj.id).value = "";
			setTimeout(function() {
				document.getElementById(consgNumberObj.id).focus();
			}, 10);
			return;
		} else {
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
		if (isDuplicateConsignment(consgNumberObj)) {
			alert("CN Number is already in Use.");
			document.getElementById(consgNumberObj.id).value = "";
			setTimeout(function() {
				document.getElementById(consgNumberObj.id).focus();
			}, 10);
			return;
		}

		showProcessing();
		url = './baBookingParcel.do?submitName=validateConsignment&consgNumber='
				+ consgNumber
				+ '&rowCount='
				+ rowCount
				+ "&bookingType="
				+ bookingType + "&baId=" + baId;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignment(req);
			}
		});

	}

}

function calPriorityRate() {
	var value = "popup";
	if (SELECTED_CONSIGNMENT_TYPE == "DOX") {
		getCNRateDtls(null, SELECTED_ROW_COUNT, value);
	} else {
		getCNRateDtlsParcel(null, SELECTED_ROW_COUNT, value);
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
				var consgNumber = document
						.getElementById("cnNumber" + rowCount).value
				var ba = document.getElementById("baCode").value;
				if (isNull(ba)) {
					document.getElementById("baCode").value = cnValidation.businessAssociate;
					document.getElementById("baId").value = cnValidation.businessAssociateId;
				}
				document.getElementById("cnNumber" + rowCount).readOnly = true;
				// Storing consigment into usedConsignments array
				// usedConsignments[rowCount] = consgNumber;
				var docTypeIdName = document.getElementById('consgTypeName').value;
				var consgType = "";
				if (docTypeIdName != null) {
					consgType = docTypeIdName.split("#")[1];
				}
				if (consgType == 'DOX') {
					validateWeightFromWM(rowCount);
					enableDisableCodAndBaAmt(consgNumber, rowCount);
				} else {
					validateWeightFromWMParcel(rowCount);
					enableDisableCodAndBaAmt(consgNumber, rowCount);
				}
			}
		}
	}
	jQuery.unblockUI();
}

function enableDisableCodAndBaAmt(consgNumber, rowCount) {
	var consgSeries = consgNumber.substring(4, 5).toUpperCase();
	if (consgSeries == "T") {
		document.getElementById("baAmt" + rowCount).readOnly = false;
		document.getElementById("codAmt" + rowCount).readOnly = false;

	} else {
		document.getElementById("baAmt" + rowCount).readOnly = true;
		document.getElementById("codAmt" + rowCount).tabindex = -1;
		document.getElementById("codAmt" + rowCount).readOnly = true;
		document.getElementById("baAmt" + rowCount).tabindex = -1;
	}
}
/**
 * validateWeightFromWeighingMachine
 * 
 * @param rowId
 * 
 * @author sdalli
 */
function validateWeightFromWMParcel(rowId) {
	if (!isNull(document.getElementById("cnNumber" + rowId).value)) {
		if (isWeighingMachineConnected) {
			getWeightFromWeighingMachineWithParam(capturedWeightInGridParcel,
					rowId);
		}
		disableEnableWeightFieldParel(rowId, isWeighingMachineConnected);
	}
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

		kgValue = document.getElementById("weightAW" + rowNo).value;
		document.getElementById("cnActualWeight" + rowNo).value = kgValue + "."
				+ gmValue;
	}
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
	weightkg = document.getElementById("weightCW" + rowNo).value;
	weightgm = document.getElementById("weightGmCW" + rowNo).value;
	document.getElementById("cnChrgWeight" + rowNo).value = weightkg + "."
			+ weightgm;

}

/**
 * redirectPage
 * 
 * @author sdalli
 */

function redirectPage() {

	var docTypeIdName = document.getElementById('consgTypeName').value;
	var docType = "";
	if (docTypeIdName != null)
		docType = docTypeIdName.split("#")[1];
	if (docType == "PPX") {
		url = "./baBookingParcel.do?submitName=viewBABookingParcel&docType="
				+ docType;
		window.location = url;
	} else if (docType == "DOX") {
		url = './baBookingDox.do?submitName=viewBABooking';
		window.location = url;
	}
}

/**
 * fnSetChargeableWeight
 * 
 * @param obj
 * @author sdalli
 */
function fnSetChargeableWeight(obj) {
	rowNo = getRowId(obj, "cnActualWeight");
	document.getElementById("cnChrgWeight" + rowNo).value = document
			.getElementById("cnActualWeight" + rowNo).value;
}
/**
 * submitForm
 * 
 * @param url
 * @param formId
 * @author sdalli
 */
function submitForm(url, formId) {
	document.forms[formId].action = url;
	document.forms[formId].submit();
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
function validateWeightFromWM(rowId) {
	if (!isNull(document.getElementById("cnNumber" + rowId).value)) {
		if (isWeighingMachineConnected) {
			getWeightFromWeighingMachineWithParam(capturedWeightInGrid, rowId);
		}
		disableEnableWeightField(rowId, isWeighingMachineConnected);
	}
}

/**
 * capturedWeightWithParam
 * 
 * @param data
 * @param rowId
 * @author sdalli
 */
function capturedWeightInGrid(data, rowId) {
	var capturedWeight = eval('(' + data + ')');
	var flag = true;
	wmWeightActual = parseFloat(wmWeightActual);
	if ((isNull(capturedWeight) || capturedWeight == -1 || capturedWeight == -2)
			&& isNull(wmWeightActual)) {
		flag = false;
		alert("Weight reached to zero.");
		var consgNumber = document.getElementById("cnNumber" + rowId).value;
		document.getElementById("cnNumber" + rowId).value = "";
		document.getElementById("cnNumber" + rowId).focus();
		document.getElementById("cnNumber" + rowId).readOnly = false;
		for ( var j = 0; j < usedConsignments.length; j++) {
			if (usedConsignments[j] == consgNumber) {
				usedConsignments[j] = null;
			}
		}
		wmWeightActual = 0.0;
		return false;
		
	} else {
		wmWeight = parseFloat(wmWeightActual) - parseFloat(capturedWeight);
		wmWeight = parseFloat(wmWeight).toFixed(3);
		wmWeightActual = parseFloat(capturedWeight).toFixed(3);
		if(wmWeight<0){
			
			//document.getElementById("weightAV" + rowId).value =0;
			//document.getElementById("weightGmAV" + rowId).value = '000';
			
			flag = false;
			alert("Negative Weight reached.");
			var consgNumber = document.getElementById("cnNumber" + rowId).value;
			document.getElementById("cnNumber" + rowId).value = "";
			document.getElementById("cnNumber" + rowId).focus();
			document.getElementById("cnNumber" + rowId).readOnly = false;
			for ( var j = 0; j < usedConsignments.length; j++) {
				if (usedConsignments[j] == consgNumber) {
					usedConsignments[j] = null;
				}
			}
			wmWeightActual = 0.0;
			return false;
			
		}else{
			
			document.getElementById("weightAV" + rowId).value = wmWeight.split(".")[0];
			document.getElementById("weightGmAV" + rowId).value = wmWeight.split(".")[1];
			
		}
		
		flag = true;
	}
	disableEnableWeightField(rowId, flag);
}

/**
 * disableEnableWeightField
 * 
 * @param rowId
 * @param flag
 * @author sdalli
 */
function disableEnableWeightField(rowId, flag) {
	document.getElementById("weightAV" + rowId).readOnly = flag;
	document.getElementById("weightGmAV" + rowId).readOnly = flag;
	if (flag = true) {
		var domWeightGM = document.getElementById("weightAV" + rowId);
		domWeightGM.tabindex = "-1";
	}
}

function setCnFocus() {
	document.getElementById("cnNumber1").focus();
}

function setRateCompomentDtls(consgRateDtls, consgRate) {
	var rateCompomentDtls = null;
	rateCompomentDtls = consgRate.rateComponentId + "#"
			+ consgRate.rateComponentCode + "#" + consgRate.calculatedValue;
	if (isNull(consgRateDtls))
		consgRateDtls = rateCompomentDtls;
	else
		consgRateDtls = consgRateDtls + "," + rateCompomentDtls;
	return consgRateDtls;
}

function validateConsigneeConsignorDetails(rowIds) {
	var isValid = true;
	var cnNumber = document.getElementById('cnNumber' + rowIds).value;
	var cnValue = cnNumber.charAt(4);
	var cnPincode = document.getElementById("cnPincode" + rowIds).value;
	var cnrPopDtls = document.getElementById("cnrdetails" + rowIds).value;
	var cnePopDtls = document.getElementById("cnedetails" + rowIds).value;

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
