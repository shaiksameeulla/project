//Add Row Funtions..	
var serialNo = 1;
var rowCount = 1;
$(document).ready(function() {
	var oTable = $('#booking').dataTable({
		"sScrollY" : "310",
		"sScrollX" : "100%",
		"sScrollXInner" : "100%",
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
	addBABookingRow();
	getWeightFromWeighingMachine();
	setCnFocus();
}

/**
 * addBABookingRow
 * 
 * @author sdalli
 */
function addBABookingRow() {

	$('#booking')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox"   id="chk' + rowCount
									+ '" name="chkBoxName" value=""/>',
							serialNo,

							'<input type="text" id="cnNumber'
									+ rowCount
									+ '" name="to.consgNumbers" size="15" maxlength="12"  class="txtbox"  onblur="convertDOMObjValueToUpperCase(this);validateConsignment(this);"  onkeypress="return OnlyAlphabetsAndNosAndEnter(this, event, \'cnPincode'
									+ rowCount
									+ '\',\'CN Number\');" /><input type="hidden" id="rowCountId" />',
							'<input type="text" id="cnPincode'
									+ rowCount
									+ '" name="to.pincodes" size="15" class="txtbox" maxlength="6" onblur="validatePincode(this);getCNRateDtls(this,'
									+ rowCount
									+ ');" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'weightGmAV'
									+ rowCount + '\');"/>',
							'<input type="text" id="destCities'
									+ rowCount
									+ '" name="to.destCities" size="15" class="txtbox" readonly="true" tabindex="-1" /><input type="hidden" id="cnrdetails'
									+ rowCount
									+ '" name="to.cnrAddressDtls"/><input type="hidden" id="cnedetails'
									+ rowCount
									+ '" name="to.cneAddressDtls"/><input type="hidden" id="deliveryTimeMapId'
									+ rowCount + '" name="to.dlvTimeMapIds"/>',

							'<input type="text" name="weight" id="weightAV'
									+ rowCount
									+ '" class="txtbox width30"  tabindex="-1" size="15" maxlength="4" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'weightGmAV'
									+ rowCount + '\');"/><span class="lable">.</span> <input type="text" name="weight" id="weightGmAV'
									+ rowCount
									+ '" class="txtbox width30"   size="11" maxlength="3" onblur="weightFormatForGmAV(this);fnSetChargeableWeightValue(this);getCNRateDtls(this,'
									+ rowCount
									+ ');"  onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'refNos'
									+ rowCount
									+ '\');"/> <input type="hidden" id="cnActualWeight'
									+ rowCount + '" name="to.actualWeights"/>',
							'<input type="text" name="weight" id="weightCW'
									+ rowCount
									+ '" class="txtbox width30"  size="11" tabindex="-1" readonly="true" /><span class="lable">.</span><input type="text" name="weight" id="weightGmCW'
									+ rowCount
									+ '" class="txtbox width30"   size="11" tabindex="-1" readonly="true" onblur="convertToFractionCW(this,3);" /><input type="hidden" id="cnChrgWeight'
									+ rowCount
									+ '" name="to.chargeableWeights"/>',
							'<input type="text" id="refNos'
									+ rowCount
									+ '" name="to.refNos"  maxlength="20" class="txtbox"  size="15"  onblur="validateChildPopUpDetailsDox(this);" onkeypress="return callEnterKeyInRefNoDox(this,event,'
									+ rowCount
									+ ');"/>\
							<input type="hidden" id="cityIds'
									+ rowCount
									+ '" name="to.cityIds" value=""/>\
							<input type="hidden" id="priorityServicedOns'
									+ rowCount
									+ '" name="to.priorityServicedOns" value=""/>\
							<input type="hidden" id="weightCapturedModes'
									+ rowCount
									+ '" name="to.weightCapturedModes" value="M"/>\
							<input type="hidden" id="pinIds'
									+ rowCount
									+ '" name="to.pincodeIds" value=""/>\
							<input type="hidden" id="consgPricingDtls'
									+ rowCount
									+ '" name="to.consgPricingDtls" value=""/>',
							'<input type="text"    class="txtbox" size="6"  id="baAmt'
									+ rowCount
									+ '" name="to.baAmts"  maxlength="6" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'codAmt'
									+ rowCount
									+ '\');" readonly="true" tabindex="-1"  />',
							'<input type="text"    class="txtbox" size="6" id="codAmt'
									+ rowCount
									+ '" name="to.codAmts"  readonly="true" tabindex="-1" onkeypress="return onlyNumberAndEnterKeyNav(event,this, \'amounts'
									+ rowCount
									+ '\');" maxlength="6"  onblur="getCNRateDtls(this,'
									+ rowCount + ',\'normal\');" />',
							'<input type="text"    class="txtbox" size="6" id="amounts'
									+ rowCount
									+ '" name="to.amounts" onfocus="" readonly="true" tabindex="-1"  />', ]);
	rowCount++;
	updateSrlNo('booking');
	serialNo++;
	return rowCount - 1;
	$("#cnNumber" + rowId).focus();
}

function callEnterKeyInRefNoDox(obj, e, rowCount) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var cnNumber = document.getElementById("cnNumber" + rowCount);
		var productCode = cnNumber.value.substring(4, 5).toUpperCase();
		if (productCode == "T") {
			document.getElementById("baAmt" + rowCount).focus();
		} else {
			getCNRateDtls(obj, rowCount, "normal");
		}
		return false;
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
	var finalWeight = parseFloat(kgValue + "." + gmValue);

//	if (gmValue.length > 0) {
//		gmValue = parseFloat(gmValue);
//	}
//	if (kgValue.length > 0) {
//		kgValue = parseFloat(kgValue);
//	}

	if (isValidPincode) {
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
	}

	var actWeight = kgValue + "." + gmValue;
	actWeight = parseFloat(actWeight).toFixed(3);
	if (!isEmptyWeight(actWeight) && !isNaN(actWeight)) {
		getDomElementById("cnActualWeight" + rowNo).value = actWeight;
		getDomElementById("weightAV" + rowNo).value = actWeight.split(".")[0];
		getDomElementById("weightGmAV" + rowNo).value = actWeight.split(".")[1];
	} else {
		document.getElementById("cnActualWeight" + rowNo).value = "0.000";
	}

	
}

function validateRateInputsDox(rowNo) {
	var isValid = true;
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnChrgWeight' + rowNo).value;
	var customerId = document.getElementById('baId').value;
	var baAmt = document.getElementById("baAmt" + rowNo).value;
	if (!isNull(consgNumber) && !isNull(pincode) && !isNull(finalWeight)
			&& !isEmptyWeight(finalWeight) && finalWeight != "NaN"
			&& !isNull(customerId) && isValidPincode == true) {
		if (productCode == "T" && (isNull(baAmt) || isNaN(baAmt))) {
			isValid = false;
		}
		return isValid;
	} else {
		return false;
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
	if (isValidPincode) {
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
	}
	document.getElementById("cnChrgWeight" + rowNo).value = chgValue;
}

/**
 * isCNEmpty
 * 
 * @param pinObj
 * @returns
 * @author sdalli
 */
function isCNEmpty(pinObj) {
	rowNo = getRowId(pinObj, "cnPincode");
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
			isValid = validateDOXFields(rowId, i);
			if (!isValid) {
				return false;
			}
		} else if (!isNull(cnNumber) && rowId > 1) {
			isValid = validateDOXFields(rowId, i);
			if (!isValid) {
				return false;
			}
		}
	}
	return isValid;
}

function validateDOXFields(rowId, count) {
	var isValid = true;

	var cnNumber = document.getElementById("cnNumber" + rowId);
	var cnPincode = document.getElementById("cnPincode" + rowId);
	var weightAW = document.getElementById("weightAV" + rowId).value;
	var weightGmAW = document.getElementById("weightGmAV" + rowId).value;
	var weightCW = document.getElementById("weightCW" + rowId).value;
	var weightGmCW = document.getElementById("weightGmCW" + rowId).value;

	if (isNull($.trim(cnNumber.value))) {
		alert("Please Enter CN Number at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode.value))) {
		alert("Please Enter Pincode at row no: " + count);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	var tempValAW = (weightAW + "." + weightGmAW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight at row no:" + count);
		setTimeout(function() {
			document.getElementById("weightGmAV" + rowId).value = "";
			document.getElementById("weightGmAV" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValCW = (weightCW + "." + weightGmCW);

	if (isEmptyWeight(tempValCW)) {
		alert("Please Enter Charge Weight at roe no.: " + count);
		setTimeout(function() {
			document.getElementById("weightGmCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	var baAmt = document.getElementById("baAmt" + rowId).value;
	var consgSeries = cnNumber.value.substring(4, 5).toUpperCase();
	if (consgSeries == "T") {
		baAmt = parseFloat(baAmt);
		if (isNull(baAmt) || isNaN(baAmt)) {
			alert("Please enter BA amount at row no: " + count);

			setTimeout(function() {
				document.getElementById('baAmt' + rowId).value = "";
				document.getElementById("baAmt" + rowId).focus();
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

	var cnNumber = document.getElementById("cnNumber" + rowId);
	var cnPincode = document.getElementById("cnPincode" + rowId);
	var weightAW = document.getElementById("weightAV" + rowId).value;
	var weightGmAW = document.getElementById("weightGmAV" + rowId).value;
	var weightCW = document.getElementById("weightCW" + rowId).value;
	var weightGmCW = document.getElementById("weightGmCW" + rowId).value;

	if (isNull($.trim(cnNumber.value))) {
		alert("Please Enter CN Number at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnNumber" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull($.trim(cnPincode.value))) {
		alert("Please Enter Pincode at row no: " + rowId);
		setTimeout(function() {
			document.getElementById("cnPincode" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValAW = (weightAW + "." + weightGmAW);
	tempValAW = parseFloat(tempValAW).toFixed(3);
	if (isEmptyWeight(tempValAW) || isNaN(tempValAW)) {
		alert("Please Enter Actual Weight " + rowId);
		setTimeout(function() {
			document.getElementById("weightGmAV" + rowId).value = "";
			document.getElementById("weightGmAV" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var tempValCW = (weightCW + "." + weightGmCW);

	if (isEmptyWeight(tempValCW)) {
		alert("Please Enter Charge Weight " + rowId);
		setTimeout(function() {
			document.getElementById("weightGmCW" + rowId).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	var baAmt = document.getElementById("baAmt" + rowId).value;
	var consgSeries = cnNumber.value.substring(4, 5).toUpperCase();
	if (consgSeries == "T") {
		baAmt = parseFloat(baAmt);
		if (isNull(baAmt) || isNaN(baAmt)) {
			alert("Please enter BA amount at row no:" + rowId);

			setTimeout(function() {
				document.getElementById('baAmt' + rowId).value = "";
				document.getElementById("baAmt" + rowId).focus();
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
		rowId = addBABookingRow();
	} else {
		rowId = parseInt(selectedRowId) + 1;
	}
	$("#cnNumber" + rowId).focus();

}

/**
 * saveOrUpdateBABookingDox
 * 
 * @author sdalli
 */
function saveOrUpdateBABookingDox() {
	if (validateMandatoryFields()) {
		showProcessing();
		url = './baBookingDox.do?submitName=saveOrUpdateBABookingDox&isWeighingMachineConnected=' + isWeighingMachineConnected;
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#baBookingDoxForm").serialize(),
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
function printCallBackSave(data) {
	var results = new Array();
	if (!isNull(data)) {
		results = data.split("#");
		if (results[0] == "SUCCESS" && results[1] == "Y") {
			alert(cnBooked);
		} else if (results[0] == "FAILURE" && results[1] == "N") {
			alert(cnNotBooked);
		} else {
			alert(cnPartiallyBooked + "\n" + results[1]);
		}
		document.baBookingDoxForm.action = "/udaan-web/baBookingDox.do?submitName=viewBABooking";
		document.baBookingDoxForm.submit();
		jQuery.unblockUI();
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
	document.getElementById("amounts" + rowCount).value="";
	document.getElementById("consgPricingDtls" + rowCount).value = "";
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

			rateDtlsFormat = finalPrice + "#" + fuelChg + "#" + riskSurChg
					+ "#" + topayChg + "#" + airportHandlingChg + "#" + splChg
					+ "#" + serviceTax + "#" + eduCessChg + "#"
					+ higherEduCessChg + "#" + freightChg;
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
			document.getElementById("cnNumber" + rowCount).readOnly = true;
			addNewRow(rowCount);

		}

	}

	jQuery.unblockUI();
}

// Calculating rate
function getCNRateDtls(Obj, rowNumber, value) {
	rowNo = rowNumber;
	var consgNumber = document.getElementById("cnNumber" + rowNo).value;
	var productCode = consgNumber.substring(4, 5);
	var pincode = document.getElementById('cnPincode' + rowNo).value;
	var finalWeight = document.getElementById('cnActualWeight' + rowNo).value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var customerId = document.getElementById('baId').value;
	var baAmt = document.getElementById("baAmt" + rowNo).value;
	var rateType = "BA";
	var consgType = "";
	var cod = document.getElementById("codAmt" + rowNo).value;
	var codAmt = "";
	if (productCode == "T" && (!isNull(cod) && !isNaN(cod))) {
		codAmt = cod;
	}
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if (validateRateInputsDox(rowNo)) {
		showProcessing();
		url = './baBookingDox.do?submitName=calculateRateForConsignment&productCode='
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
				+ "&priorityService="
				+ SELECTED_SERVICED_ON
				+ "&consgNumber="
				+ consgNumber
				+ "&codAmt=" + codAmt;
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

/**
 * cancelBABookingDoxDetails
 * 
 * @author sdalli
 */
function cancelBABookingDoxDetails() {
	var flag = confirm("Do you want to cancel the details ?");
	if (flag) {
		url = "baBookingDox.do?submitName=viewBABooking";
		document.baBookingDoxForm.action = url;
		document.baBookingDoxForm.submit();
	}
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

function validateChildPopUpDetailsDox(obj) {
	var isValid = true;
	var rowIds = getRowId(obj, "refNos");
	var cnNumber = document.getElementById('cnNumber' + rowIds).value;
	var cnValue = cnNumber.charAt(4);
	var cnPincode = document.getElementById("cnPincode" + rowIds).value;
	var cnrPopDtls = document.getElementById("cnrdetails" + rowIds).value;
	var cnePopDtls = document.getElementById("cnedetails" + rowIds).value;
	if (!isNull(cnPincode)) {
		if (cnValue == "p" || cnValue == "P" || cnValue == "t"
				|| cnValue == "T") {
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
	// getCNRateDtls(obj, rowIds, "normal");

}
