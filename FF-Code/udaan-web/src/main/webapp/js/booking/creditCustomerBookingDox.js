//Add Row Funtions..	
/** The serialNo */
var serialNo = 1;
/** The rowCount */
var rowCount = 1;
$(document).ready(function() {
	var oTable = $('#booking').dataTable({
		"sScrollY" : "310",
		"sScrollX" : "100%",
		"sScrollXInner" : "125%",
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
});

/**
 * loadDefaultObjects
 * 
 * @author sdalli
 */
function loadDefaultObjects() {
	//fnClickAddRow();
	getWeightFromWeighingMachine();
	setCnFocus();
}

/**
 * fnClickAddRow
 * 
 * @author sdalli
 */
function fnClickAddRow() {

	$('#booking')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"   id="chk' + rowCount
									+ '" name="chkBoxName" value=""/>',
							serialNo,

							'<input type="text" id="cnNumber'
									+ rowCount
									+ '" name="to.consgNumbers" size="15" maxlength="12" class="txtbox" onblur="convertDOMObjValueToUpperCase(this);validateConsignmentCCBookingDox(this);" onchange="" onkeypress="return OnlyAlphabetsAndNosAndEnter(this, event, \'cnPincode'
									+ rowCount
									+ '\',\'CN Number\');" /><input type="hidden" id="cnrdetails'
									+ rowCount
									+ '" name="to.cnrAddressDtls"/><input type="hidden" id="cnedetails'
									+ rowCount
									+ '" name="to.cneAddressDtls"/><input type="hidden" id="deliveryTimeMapId'
									+ rowCount + '" name="to.dlvTimeMapIds"/>',
							'<input type="text" id="cnCustCode'
									+ rowCount
									+ '" name="to.customerCode" size="28"  readOnly="true" class="txtbox" onkeypress="return onlyDecimal(event);"   onchange="validateCreditCustCode(this);" />',
							'<input type="text" id="cnPincode'
									+ rowCount
									+ '" name="to.pincodes" size="7" class="txtbox" maxlength="6"  onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'weightGmAV'
									+ rowCount
									+ '\');" onblur="validatePincode(this);getCNRateDtls('
									+ rowCount + ');" />',
							'<input type="text" id="destCities'
									+ rowCount
									+ '" name="to.destCities" size="15" class="txtbox" readonly="true" tabindex="-1" />',

							'<input type="text" name="weight" id="weightAV'
									+ rowCount
									+ '" class="txtbox width30"  size="11" maxlength="4" tabindex="-1"  onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'weightGmAV'
									+ rowCount
									+ '\');"  /><span class="lable">.</span> <input type="text" name="weight" id="weightGmAV'
									+ rowCount
									+ '" class="txtbox width30"   size="11"  maxlength="3" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'refNos'
									+ rowCount
									+ '\');"  onblur="weightFormatForGmAV(this);fnSetChargeableWeightValue(this);getCNRateDtls('
									+ rowCount
									+ ');" /> <input type="hidden" id="cnActualWeight'
									+ rowCount + '" name="to.actualWeights"/>',
							'<input type="text" name="weight" id="weightCW'
									+ rowCount
									+ '" class="txtbox width30"  size="11" tabindex="-1" readonly="true" onkeypress="return onlyNumeric(event);"  /><span class="lable">.</span><input type="text" name="weight" id="weightGmCW'
									+ rowCount
									+ '" class="txtbox width30"   size="11" tabindex="-1" readonly="true" onkeypress="return onlyNumeric(event);"  /><input type="hidden" id="cnChrgWeight'
									+ rowCount
									+ '" name="to.chargeableWeights"/>',
							'<input type="text" id="refNos'
									+ rowCount
									+ '" name="to.refNos" maxlength="30" class="txtbox"  size="10" onblur="validateChildPopUpDetailsDox(this);"  onkeypress="return callEnterKeyInRefNoDox(this,event,'
									+ rowCount
									+ ');"/>\
							<input type="hidden" id="cityIds'
									+ rowCount
									+ '" name="to.cityIds" value=""/>\
							<input type="hidden" id="priorityServicedOns'
									+ rowCount
									+ '" name="to.priorityServicedOns" value=""/>\
							<input type="hidden" id="customerIds'
									+ rowCount
									+ '" name="to.customerIds" value=""/>\
							<input type="hidden" id="weightCapturedModes'
									+ rowCount
									+ '" name="to.weightCapturedModes" value="M"/>\
							<input type="hidden" id="pinIds'
									+ rowCount
									+ '" name="to.pincodeIds" value=""/>\
							<input type="hidden" id="bookingIds'
									+ rowCount
									+ '" name="to.bookingIds" value=""/>\
							<input type="hidden" id="pickupRunsheetNos'
									+ rowCount
									+ '" name="to.pickupRunsheetNos" value=""/>\
							<input type="hidden" id="consgPricingDtls'
									+ rowCount
									+ '" name="to.consgPricingDtls" value=""/>',
							'<input type="text" maxlength="6"   class="txtbox" size="6" id="codOrLcAmts'
									+ rowCount
									+ '" name="to.codOrLCAmts" maxlength="10" onkeypress="return callEnterKeyInLCAmtDox(this,event,'
									+ rowCount + ');"  disabled="true"/>',
							'<input type="text" class="txtbox" size="6" maxlength="20" id="lcBankNames'
									+ rowCount
									+ '" name="to.lcBankNames" value="" onblur="getCNRateDtls('
									+ rowCount
									+ ',\'normal\');"  onkeypress="return callEnterKey(event, document.getElementById(\'amounts'
									+ rowCount + '\'));"  disabled="true"/>',
							'<input type="text"    class="txtbox" size="6" id="amounts'
									+ rowCount
									+ '" name="to.amounts" onfocus="" readonly="true" tabindex="-1"  />', ]);

	rowCount++;
	serialNo++;
	updateSrlNo('booking');
	return rowCount - 1;
}

/**
 * validateCreditCustCode
 * 
 * @param obj
 * @author sdalli
 */
function validateCreditCustCode(obj) {

	var rowNo = getRowId(obj, "cnCustCode");
	var custCode = document.getElementById('cnCustCode' + rowNo).value;
	if (custCode.length != 10) {
		alert("Customer Code Should be 10 digits.");
		document.getElementById("cnCustCode" + rowNo).value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return;
	}

	ROW_COUNT = rowNo;
	url = './creditCustomerBookingParcel.do?submitName=validateCustCode&custCode='
			+ custCode;
	ajaxCallWithoutForm(url, validateCreditCustCodeResponse);
}

/**
 * validateCreditCustCodeResponse
 * 
 * @param data
 * @author sdalli
 */
function validateCreditCustCodeResponse(data) {
	var custDetails = data;
	if (custDetails.isCustValid == "N") {
		alert('Customer code not valid');
		document.getElementById("cnCustCode" + ROW_COUNT).value = "";
		setTimeout(function() {
			document.getElementById("cnCustCode" + ROW_COUNT).focus();
		}, 10);
		return;
	} else if (custDetails.isCustValid == "Y") {
		document.getElementById('customerIds' + ROW_COUNT).value = custDetails.custID;
		document.getElementById("cnCustCode" + ROW_COUNT).value = custDetails.custCode;
	}
}

/**
 * weightFormatForGmAV
 * 
 * @param weightObj
 * @author sdalli
 */
function weightFormatForGmAV(weightObj) {
	var rowNo = getRowId(weightObj, "weightGmAV");
	var gmValue = document.getElementById("weightGmAV" + rowNo).value;
	var kgValue = document.getElementById("weightAV" + rowNo).value;

	// var finalWeight = parseFloat(kgValue + "." + gmValue);

	/*
	 * if (gmValue.length > 0) { gmValue = parseFloat(gmValue); } if
	 * (kgValue.length > 0) { kgValue = parseFloat(kgValue); }
	 */

	if (!isNull(gmValue)) {
		if (gmValue.length == 0) {
			document.getElementById("weightGmAV" + rowNo).value = "000";
			gmValue += "000";
		} else if (gmValue.length == 1) {
			document.getElementById("weightGmAV" + rowNo).value += "00";
			gmValue += "00";
		} else if (gmValue.length == 2) {
			document.getElementById("weightGmAV" + rowNo).value += "0";
			gmValue += "0";
		}

	}

	if (isNull(kgValue)) {
		kgValue = "0";
	}

	var actWeight = kgValue + "." + gmValue;
	actWeight = parseFloat(actWeight).toFixed(3);
	if (!isEmptyWeight(actWeight) && !isNaN(actWeight)) {
		getDomElementById("cnActualWeight" + rowNo).value = actWeight;
		/*
		 * getDomElementById("weightAV" + rowNo).value =
		 * actWeight.split(".")[0]; getDomElementById("weightGmAV" +
		 * rowNo).value = actWeight.split(".")[1];
		 */
	} else {
		document.getElementById("cnActualWeight" + rowNo).value = "0.000";
	}

}

/**
 * fnSetChargeableWeightValue
 * 
 * @param weightGMObj
 * @author sdalli
 */
function fnSetChargeableWeightValue(weightGMObj) {
	var rowNo = getRowId(weightGMObj, "weightGmAV");
	var chgValue = document.getElementById("cnActualWeight" + rowNo).value;
	var keyValuekg = chgValue.split(".")[0];
	var keyValueGm = chgValue.split(".")[1];
	if (keyValuekg != "" && keyValuekg != null && keyValuekg != "null"
			&& keyValuekg != undefined && isNANCheck(keyValuekg)) {

		if (keyValuekg.length == 0) {
			document.getElementById("weightCW" + rowNo).value += "0";
			keyValuekg += "0";
		}
		document.getElementById("weightCW" + rowNo).value = keyValuekg;
	} else {
		document.getElementById("weightCW" + rowNo).value = "0";
		// keyValuekg="0";
	}

	if (keyValueGm != "" && keyValueGm != null && keyValueGm != "null"
			&& keyValueGm != undefined && isNANCheck(keyValuekg)) {
		if (keyValueGm.length == 0) {
			document.getElementById("weightGmCW" + rowNo).value = "000";
			keyValueGm += "000";
		} else if (keyValueGm.length == 1) {
			document.getElementById("weightGmCW" + rowNo).value += "00";
			keyValueGm += "00";
		} else if (keyValueGm.length == 2) {
			document.getElementById("weightGmCW" + rowNo).value += "0";
			keyValueGm += "0";
		}

		document.getElementById("weightGmCW" + rowNo).value = keyValueGm;
	} else {

		document.getElementById("weightGmCW" + rowNo).value = "000";
		// keyValueGm = "000";
	}
	document.getElementById("cnChrgWeight" + rowNo).value = chgValue;
}

/**
 * validateMandatoryFields
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function validateMandatoryFields() {
	var isValid = true;
	var table = document.getElementById("booking");
	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var cnNumber = document.getElementById("cnNumber" + rowId).value;
		if (rowId == 1) {
			isValid = validateDoxMandatoryFields(rowId, i);
			if (!isValid) {
				return false;
			}
		} else if (!isNull(cnNumber) && rowId > 1) {
			isValid = validateDoxMandatoryFields(rowId, i);
			if (!isValid) {
				return false;
			}
		}
	}
	return isValid;
}

function validateDoxMandatoryFields(rowId, count) {
	var isValid = true;
	var cnNumber = document.getElementById("cnNumber" + rowId).value;
	var cnCustCode = document.getElementById("cnCustCode" + rowId);
	var cnPincode = document.getElementById("cnPincode" + rowId);
	var weightAW = document.getElementById("weightAV" + rowId).value;
	var weightGmAW = document.getElementById("weightGmAV" + rowId).value;
	var weightCW = document.getElementById("weightCW" + rowId).value;
	var weightGmCW = document.getElementById("weightGmCW" + rowId).value;
	var codOrLcAmt = document.getElementById("codOrLcAmts" + rowId).value;
	var consgSeries = cnNumber.substring(4, 5).toUpperCase();
	var lcBankName = document.getElementById("lcBankNames" + rowId).value;

	if (isNull($.trim(cnNumber))) {
		alert("Please enter consignment number at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnCustCode.value))) {
		alert("Please enter customer code at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnCustCode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode.value))) {
		alert("Please enter pincode at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValAW = (weightAW + "." + weightGmAW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight " + count);
		setTimeout(function() {
			document.getElementById("weightGmAV" + rowId).value = "";
			document.getElementById("weightGmAV" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValCW = (weightCW + "." + weightGmCW);
	if (isEmptyWeight(tempValCW)) {
		alert("Please Enter Charge Weight " + count);
		setTimeout(function() {
			document.getElementById("weightGmCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (consgSeries == "L" || consgSeries == "D") {
		codOrLcAmt = parseFloat(codOrLcAmt);
		if (isNull(codOrLcAmt) || isNaN(codOrLcAmt)) {
			if (consgSeries == "L") {
				alert("Please enter cod amount at row no: " + count);
			} else if (consgSeries == "D") {
				alert("Please enter LC amount at row no: " + count);
			}
			setTimeout(function() {
				document.getElementById('codOrLcAmts' + rowId).value = "";
				document.getElementById("codOrLcAmts" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
	if (consgSeries == "D") {
		if (isNull($.trim(lcBankName))) {
			alert("Please enter LC Bank name at row no: " + count);
			setTimeout(function() {
				document.getElementById("lcBankNames" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	var amount = document.getElementById("amounts" + rowId).value;
	if (isNull($.trim(amount))) {
		alert("Please calculate rate for consignment at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	isValid = validateConsigneeConsignorDetails(rowId);
	return isValid;
}

function validateFields(rowId, count) {
	var isValid = true;
	var cnNumber = document.getElementById("cnNumber" + rowId).value;
	var cnCustCode = document.getElementById("cnCustCode" + rowId);
	var cnPincode = document.getElementById("cnPincode" + rowId);
	var weightAW = document.getElementById("weightAV" + rowId).value;
	var weightGmAW = document.getElementById("weightGmAV" + rowId).value;
	var weightCW = document.getElementById("weightCW" + rowId).value;
	var weightGmCW = document.getElementById("weightGmCW" + rowId).value;
	var codOrLcAmt = document.getElementById("codOrLcAmts" + rowId).value;
	var consgSeries = cnNumber.substring(4, 5).toUpperCase();
	var lcBankName = document.getElementById("lcBankNames" + rowId).value;

	if (isNull($.trim(cnNumber))) {
		alert("Please enter consignment number at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnCustCode.value))) {
		alert("Please enter customer code at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnCustCode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode.value))) {
		alert("Please enter pincode at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValAW = (weightAW + "." + weightGmAW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight at row no.:" + rowId);
		setTimeout(function() {
			document.getElementById("weightGmAV" + rowId).value = "";
			document.getElementById("weightGmAV" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValCW = (weightCW + "." + weightGmCW);
	if (isEmptyWeight(tempValCW)) {
		alert("Please Enter Charge Weight  at row no.: " + rowId);
		setTimeout(function() {
			document.getElementById("weightGmCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (consgSeries == "L" || consgSeries == "D") {
		codOrLcAmt = parseFloat(codOrLcAmt);
		if (isNull(codOrLcAmt) || isNaN(codOrLcAmt)) {
			if (consgSeries == "L") {
				alert("Please enter cod amount at row no: " + rowId);
			} else if (consgSeries == "D") {
				alert("Please enter LC amount at row no: " + rowId);
			}
			setTimeout(function() {
				document.getElementById('codOrLcAmts' + rowId).value = "";
				document.getElementById("codOrLcAmts" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
	if (consgSeries == "D") {
		if (isNull($.trim(lcBankName))) {
			alert("Please enter LC Bank name at row no: " + rowId);
			setTimeout(function() {
				document.getElementById("lcBankNames" + rowId).focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	return isValid;
}

/**
 * addNewRow
 * 
 * @param rowNo
 * @author sdalli
 */
/*
 * function addNewRow(rowNo) { //rowNo = getRowId(obj, "cnActualWeight"); var
 * nextRow = parseInt(rowNo) + 1;
 * 
 * if(eval(document.getElementById("cnNumber"+nextRow))==null){ fnClickAddRow();
 * document.getElementById("cnNumber"+nextRow).focus(); } }
 */

/**
 * addNewRow
 * 
 * @param rowNo
 * @author sdalli
 */
function addNewRow(selectedRowId) {
	var table = document.getElementById("booking");
	var lastRow = table.rows.length - 1;
	var isNewRow = false;

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		if (rowId == selectedRowId && i == lastRow) {
			isNewRow = true;
		}
	}
	var rowId = 0;
	if (isNewRow) {
		rowId = fnClickAddRow();
	} else {
		rowId = parseInt(selectedRowId) + 1;
	}
	$("#cnNumber" + rowId).focus();

	/*
	 * var nextRow = parseInt(rowNo) + 1; if
	 * (eval(document.getElementById("cnNumber" + nextRow)) == null) {
	 * addBABookingRow(); document.getElementById("cnNumber" + nextRow).focus(); }
	 */

}

/**
 * saveOrUpdateSaveCreditBookingDox
 * 
 * @author sdalli
 */
function saveOrUpdateSaveCreditBookingDox() {
	if (validateMandatoryFields()) {
		jQuery(":input").attr("disabled", false);
		showProcessing();
		url = './creditCustomerBookingDox.do?submitName=saveOrUpdateCreditCustBookingDox&isWeighingMachineConnected=' + isWeighingMachineConnected;
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#creditCustomerBookingDoxForm").serialize(),
			success : function(req) {
				printCallBackSave(req);
			}
		});
	}
}
/**
 * printCallBackSave
 * 
 * @param data
 * @author sdalli
 */
/*
 * function printCallBackSave(data) { if (data == "SUCCESS") {
 * alert("Consignment booked successfully."); } else if (data == "FAILURE") {
 * alert("Exception occurred. Consignment not booked."); } }
 */
function printCallBackSave(data) {
	var results = new Array();
	results = data.split("#");
	if (results[0] == "SUCCESS" && results[1] == "Y") {
		alert(cnBooked);
	} else if (results[0] == "FAILURE" && results[1] == "N") {
		alert(cnNotBooked);
	} else {
		alert(cnPartiallyBooked + "\n" + results[1]);
	}
	document.creditCustomerBookingDoxForm.action = "/udaan-web/creditCustomerBookingDox.do?submitName=viewCreditCustomerBookingDox";
	document.creditCustomerBookingDoxForm.submit();
	jQuery.unblockUI();
}

// Calculating rate

function getCNRateDtls(rowNo, value) {
	// rowNo = getRowId(Obj, "refNos");
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;

	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var customerId = document.getElementById('customerIds' + rowNo).value;
	var rateType = "CC";
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var orgCity = document.getElementById('originCity').value;
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	var codOrLcAmt = document.getElementById('codOrLcAmts' + rowNo).value;
	var lcBankName = document.getElementById('lcBankNames' + rowNo).value;
	var lcAmt = "";
	var codAmt = "";
	codOrLcAmt = parseFloat(codOrLcAmt);
	if (productCode == "L" && (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		codAmt = codOrLcAmt;
	} else if (productCode == "D" && (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		lcAmt = codOrLcAmt;
	} else if (productCode == "T"
			&& (!isNull(codOrLcAmt) && !isNaN(codOrLcAmt))) {
		codAmt = codOrLcAmt;
	}
	if (validateRateInputs(rowNo)) {
		showProcessing();
		url = './creditCustomerBookingDox.do?submitName=calculateRateForConsignment&productCode='
				+ productCode
				+ "&consgType="
				+ consgType
				+ "&destPincode="
				+ pincode
				+ "&consgWeight="
				+ finalWeight
				+ "&rateType="
				+ rateType
				+ "&bookingOfficeId="
				+ originOfficeId
				+ "&customerId="
				+ customerId
				+ "&consgNumber="
				+ consgNumber
				+ "&priorityService="
				+ SELECTED_SERVICED_ON
				+ "&lcBankName="
				+ lcBankName + "&codAmt=" + codAmt + "&lcAmt=" + lcAmt;
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				printCallBackCNRateDetails(req, rowNo);
			}
		});
	} else {

		if (!isNull(value)) {
			if (value == "popup") {
				document.getElementById("weightGmAV" + rowNo).focus();
			} else {
				validateFields(rowNo);
			}
		}
	}
}

function printCallBackCNRateDetails(data, rowCount) {
	var rateDtlsFormat = "";
	var finalPrice = "";
	var fuelChg = "";
	var riskSurChg = "";
	var topayChg = "";
	var airportHandlingChg = "";
	var splChg = "";
	var serviceTax = "";
	var eduCessChg = "";
	var higherEduCessChg = "";
	var freightChg = "";
	var rateType = "R";
	document.getElementById("consgPricingDtls" + rowCount).value = "";
	document.getElementById("amounts" + rowCount).value="";
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			setTimeout(function() {
				document.getElementById("cnNumber" + rowCount).focus();
			}, 10);
			// addNewRow(rowNo);
		} else {
			if (cnRateDetails.finalPrice != null
					&& cnRateDetails.finalPrice > 0) {
				finalPrice = cnRateDetails.finalPrice;
			} else {
				finalPrice = "0.00";
			}
			if (cnRateDetails.fuelChg != null && cnRateDetails.fuelChg > 0) {
				fuelChg = cnRateDetails.fuelChg;
			} else {
				fuelChg = "0.00";
			}
			if (cnRateDetails.riskSurChg != null
					&& cnRateDetails.riskSurChg > 0) {
				riskSurChg = cnRateDetails.riskSurChg;
			} else {
				riskSurChg = "0.00";
			}
			if (cnRateDetails.topayChg != null && cnRateDetails.topayChg > 0) {
				topayChg = cnRateDetails.topayChg;
			} else {
				topayChg = "0.00";
			}
			if (cnRateDetails.airportHandlingChg != null
					&& cnRateDetails.airportHandlingChg > 0) {
				airportHandlingChg = cnRateDetails.airportHandlingChg;
			} else {
				airportHandlingChg = "0.00";
			}
			if (cnRateDetails.splChg != null && cnRateDetails.splChg > 0) {
				splChg = cnRateDetails.splChg;
			} else {
				splChg = "0.00";
			}
			if (cnRateDetails.serviceTax != null
					&& cnRateDetails.serviceTax > 0) {
				serviceTax = cnRateDetails.serviceTax;
			} else {
				serviceTax = "0.00";
			}
			if (cnRateDetails.eduCessChg != null
					&& cnRateDetails.eduCessChg > 0) {
				eduCessChg = cnRateDetails.eduCessChg;
			} else {
				eduCessChg = "0.00";
			}
			if (cnRateDetails.higherEduCessChg != null
					&& cnRateDetails.higherEduCessChg > 0) {
				higherEduCessChg = cnRateDetails.higherEduCessChg;
			} else {
				higherEduCessChg = "0.00";
			}
			if (cnRateDetails.freightChg != null
					&& cnRateDetails.freightChg > 0) {
				freightChg = cnRateDetails.freightChg;
			} else {
				freightChg = "0.00";
			}

			if (cnRateDetails.rateType != null && cnRateDetails.rateType != "") {
				rateType = cnRateDetails.rateType;
			}

			rateDtlsFormat = finalPrice + "#" + fuelChg + "#" + riskSurChg
					+ "#" + topayChg + "#" + airportHandlingChg + "#" + splChg
					+ "#" + serviceTax + "#" + eduCessChg + "#"
					+ higherEduCessChg + "#" + freightChg + "#" + rateType;
			document.getElementById("consgPricingDtls" + rowCount).value = rateDtlsFormat;
			document.getElementById("amounts" + rowCount).value = finalPrice;
			validateFields(rowCount);
			var amount = document.getElementById("amounts" + rowCount).value;
			if (isNull(amount)) {
				alert("Please calculate rate for consignment at row no: "
						+ rowCount);
				setTimeout(function() {
					document.getElementById("cnNumber" + rowCount).focus();
				}, 10);
				isValid = false;
				return isValid;
			}
			addNewRow(rowCount);
		}

	}
	jQuery.unblockUI();
}

/**
 * cancelCreditCustBookingDoxDetails
 * 
 * @author sdalli
 */
function cancelCreditCustBookingDoxDetails() {
	var flag = confirm("Do you want to cancel the details ?");
	if (flag) {
		url = "/udaan-web/creditCustomerBookingDox.do?submitName=viewCreditCustomerBookingDox";
		document.creditCustomerBookingDoxForm.action = url;
		document.creditCustomerBookingDoxForm.submit();
	}
}

/* Checking Customer Code is Empty or Not onfouse event. */
/**
 * isCustCodeValied
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isCustCodeValied(pinObj) {
	rowNo = getRowId(pinObj, "cnCustCode");
	var cnNumber = document.getElementById("cnCustCode" + rowNo);
	if (cnNumber.value.length != 6) {
		alert("Customer Code Should be 6 digits.");
		setTimeout(function() {
			document.getElementById("cnCustCode" + rowNo).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
}

/* Checking Consigment Number is Empty or Not onfouse event. */
/**
 * isCNEmpty
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isCNEmpty(pinObj) {
	rowNo = getRowId(pinObj, "cnNumber");
	var cnNumber = document.getElementById("cnNumber" + rowNo);
	if (isNull(cnNumber.value)) {
		alert("Please Enter CN Number.");
		setTimeout(function() {
			document.getElementById("cnNumber" + rowNo).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
}

/* Checking Customer Code is Empty or Not onfouse event. */
/**
 * isCustCodeEmpty
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isCustCodeEmpty(pinObj) {
	rowNo = getRowId(pinObj, "cnPincode");
	var cnNumber = document.getElementById("cnCustCode" + rowNo);
	if (isNull(cnNumber.value)) {
		alert("Please Enter Customer Code.");
		setTimeout(function() {
			document.getElementById("cnCustCode" + rowNo).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
}

/* Checking PineCode Number is Empty or Not onfouse event. */
/**
 * isPinEmpty
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isPinEmpty(pinObj) {
	rowNo = getRowId(pinObj, "cnActualWeight");
	var cnNumber = document.getElementById("cnPincode" + rowNo);
	if (isNull(cnNumber.value)) {
		alert("Please Enter Pincode.");
		setTimeout(function() {
			document.getElementById("cnPincode" + rowNo).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
}

/**
 * validateConsignmentCCBookingDox
 * 
 * @param consgNumberObj
 * @author sdalli
 */
function validateConsignmentCCBookingDox(consgNumberObj) {
	if(consgNumberObj.readOnly==false){
	if (isDuplicateConsignment(consgNumberObj)) {
		alert("CN Number is already in Use.");
		document.getElementById(consgNumberObj.id).value = "";
		setTimeout(function() {
			document.getElementById(consgNumberObj.id).focus();
		}, 10);
		return;
	} else {
		validateConsignmentCredit(consgNumberObj);
	}
	}
}

/**
 * validateConsignmentCredit
 * 
 * @param consgNumberObj
 * @author sdalli
 */
function validateConsignmentCredit(consgNumberObj) {
	consgNumberObj.value = $.trim(consgNumberObj.value);
	var rowCount = getRowId(consgNumberObj, "cnNumber");
	var bookingType = document.getElementById("bookingType").value;
	if (consgNumberObj.value != null && consgNumberObj.value != "") {
		var consgNumber = consgNumberObj.value;
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
		showProcessing();
		url = './creditCustomerBookingDox.do?submitName=validateConsignment&consgNumber='
				+ consgNumber
				+ '&rowCount='
				+ rowCount
				+ "&bookingType="
				+ bookingType;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignmentCredit(req);
			}
		});

	}

}

/**
 * isDuplicateConsignment
 * 
 * @param consgNumberObj
 * @returns {Boolean}
 * @author sdalli
 */
function isDuplicateConsignment(consgNumberObj) {
	var table = document.getElementById("booking");
	var selectedRowId = getRowId(consgNumberObj, "cnNumber");

	for ( var i = 1; i < table.rows.length; i++) {
		var rowId = table.rows[i].cells[0].childNodes[0].id.substring(3);
		var cnNumber = document.getElementById("cnNumber" + rowId).value;
		if (consgNumberObj.value.toUpperCase() == cnNumber
				&& rowId != selectedRowId) {
			return true;
		}
	}
	return false;
}

/**
 * printCallBackConsignmentCredit
 * 
 * @param data
 * @author sdalli
 */
function printCallBackConsignmentCredit(data) {

	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		// bookingIds = cnValidation.bookingId;
		// pickupRunsheetNos = cnValidation.pickupRunsheetNo;
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
					document.getElementById("cnCustCode" + rowCount).value = cnValidation.custCode;
					document.getElementById("customerIds" + rowCount).value = cnValidation.custID;
				}
				document.getElementById("cnNumber" + rowCount).readOnly = true;
				var consgNumber = document
						.getElementById("cnNumber" + rowCount).value;
				enableDisableCodAndLCAmt(consgNumber, rowCount);
				validateWeightFromWM(rowCount);
			}

		}

		jQuery.unblockUI();
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

		/*
		 * if (!isNull(wmWeightActual)) { document.getElementById("weightAV" +
		 * rowId).value = wmWeightActual .split(".")[0];
		 * document.getElementById("weightGmAV" + rowId).value = wmWeightActual
		 * .split(".")[1]; }
		 */
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
}

function callEnterKeyInRefNoDox(obj, e, rowCount) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var consgNumber = document.getElementById("cnNumber" + rowCount).value;
		var productCode = consgNumber.substring(4, 5).toUpperCase();
		if (productCode == "L" || productCode == "T" || productCode == "D") {
			document.getElementById("codOrLcAmts" + rowCount).focus();

		} else {
			getCNRateDtls(rowCount, "normal");
		}
		return false;
	}
}

function callEnterKeyInLCAmtDox(obj, e, rowCount) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key > 31 && (key < 48 || key > 57)) {
		return false;
	} else if (key == 13) {
		var consgNumber = document.getElementById("cnNumber" + rowCount).value;
		var productCode = consgNumber.substring(4, 5).toUpperCase();
		if (productCode == "D") {
			document.getElementById("lcBankNames" + rowCount).focus();
		} else {
			getCNRateDtls(rowCount, "normal");

		}
		return false;
	}
}

function validateRateInputs(rowNo) {
	var isValid = true;
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var customerId = document.getElementById('customerIds' + rowNo).value;
	var codOrLcAmt = document.getElementById('codOrLcAmts' + rowNo).value;
	var lcBankName = document.getElementById('lcBankNames' + rowNo).value;
	codOrLcAmt = parseFloat(codOrLcAmt);
	if (!isNull(consgNumber) && !isNull(pincode) && !isNull(finalWeight)
			&& !isNaN(finalWeight) && !isNull(customerId)
			&& !isEmptyWeight(finalWeight)) {
		if (productCode == "L" && (isNull(codOrLcAmt) || isNaN(codOrLcAmt))
				&& isValidPincode == true) {
			isValid = false;
		} else if (productCode == "D"
				&& (isNull(codOrLcAmt) || isNaN(codOrLcAmt) || isNull(lcBankName))) {
			isValid = false;
		}

		return isValid;
	} else {
		return false;
	}

}
function validateChildPopUpDetailsDox(obj) {
	var isValid = true;
	var rowIds = getRowId(obj, "refNos");
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

}