/**
 * loadDefaultValues
 * 
 * @author sdalli
 */
function loadDefaultValues() {
	getApprovers();
	getWeightFromWeighingMachine();
	document.getElementById("approver").focus();
}

/**
 * saveOrUpdateFOCBookingDox
 * 
 * @author sdalli
 */
function saveOrUpdateFOCBookingDox() {
	if (validateFOCBookingFields()) {
		showProcessing();
		var url = './focBookingDox.do?submitName=saveOrUpdateFOCBookingDox';
		ajaxCall(url, "fOCBookingDoxForm", printCallBackSave);

	}
}

/**
 * printCallBackSave
 * 
 * @param data
 * @author sdalli
 */
function printCallBackSave(data) {
	var bookRes = new Array();
	bookRes = data.split("#");
	if (bookRes[0] == "SUCCESS") {
		alert(bookRes[1]);
		if (confirm("Do You want to print?")) {
			printFOCBookingDox();
		}
	} else if (bookRes[0] == "FAILURE") {
		alert(bookRes[1]);
	}
	document.fOCBookingDoxForm.action = "/udaan-web/focBookingDox.do?submitName=viewFOCBooking";
	document.fOCBookingDoxForm.submit();
	jQuery.unblockUI();
}

/*
 * ******Start******** Print Dox code
 */

function printFOCBookingDox() {
	var consgNumber = $("#cnNumber").val();
	url = './focBookingDox.do?submitName=printFOCBooking&consgNumber='
			+ consgNumber;
	window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left= 412,top = 184,scrollbars=yes');
}

/*
 * ******End******** Print Dox code
 */

/**
 * cancelFOCBookingDetails
 * 
 * @author sdalli
 */
function cancelFOCBookingDetails() {

	if (promptConfirmation("cancel")) {
		document.fOCBookingDoxForm.action = "/udaan-web/focBookingDox.do?submitName=viewFOCBooking";
		document.fOCBookingDoxForm.submit();
	}
}

/**
 * validateFOCBookingFields
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function validateFOCBookingFields() {
	var isValid = true;
	var CNnumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var dest = document.getElementById('destCity').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var cnrName = document.getElementById('cnrName').value;
	var cnrMobile = document.getElementById('cnrMobile').value;
	var cnrPhone = document.getElementById('cnrPhone').value;
	var cneName = document.getElementById('cneName').value;
	var cneMobile = document.getElementById('cneMobile').value;
	var cnePhone = document.getElementById('cnePhone').value;
	var approver = document.getElementById('approver').value;
	var errorMsgs = "";
	var focusObj = "";

	if (isNull($.trim(CNnumber))) {
		document.getElementById("cnNumber").value="";
		errorMsgs = "Please enter the CN Number.\n";
		focusObj = "cnNumber";

	}

	if (isNull(approver)) {
		errorMsgs = "Please select the approver.\n";
		focusObj = "approver";

	}
	if (isNull($.trim(pincode))) {
		errorMsgs = errorMsgs + "Please enter the Pincode.\n";
		if (isNull(focusObj)) {
			focusObj = "pinCode";
		}

	}
	if (isNull($.trim(dest))) {
		errorMsgs = errorMsgs + "Please enter the Destination.\n";
		if (isNull(focusObj)) {
			focusObj = "dest";
		}

	}
	if (isEmptyWeight(finalWeight)) {
		errorMsgs = errorMsgs + "Please enter the Weight.\n";
		if (isNull(focusObj)) {
			focusObj = "weightGm";
		}

		document.getElementById("weightGm").value = "";
		document.getElementById("weight").value = "";

	}
	if (isNull($.trim(cnrName))) {
		document.getElementById("cnrName").value="";
		errorMsgs = errorMsgs + "Please enter the Consignor Name.\n";
		if (isNull(focusObj)) {
			focusObj = "cnrName";
		}

	}
	if (isNull($.trim(cnrPhone))) {
		if (isNull($.trim(cnrMobile))) {
			errorMsgs = errorMsgs
					+ "Please enter the Mobile Number for Consignor.\n";
			if (isNull(focusObj)) {
				focusObj = "cnrMobile";
			}

		}
	}
	if (isNull($.trim(cnrMobile))) {
		if (isNull($.trim(cnrPhone))) {
			errorMsgs = errorMsgs + "Please enter the CN Phone No.\n";
			if (isNull(focusObj)) {
				focusObj = "cnrPhone";
			}

		}
	}

	if (isNull($.trim(cneName))) {
		document.getElementById("cneName").value="";
		errorMsgs = errorMsgs + "Please enter the Consignee Name.\n";
		if (isNull(focusObj)) {
			focusObj = "cneName";
		}

	}
	if (isNull($.trim(cnePhone))) {
		if (isNull($.trim(cneMobile))) {
			errorMsgs = errorMsgs
					+ "Please enter the Mobile Number for Consignee.\n";
			if (isNull(focusObj)) {
				focusObj = "cneMobile";
			}

		}
	}
	if (isNull($.trim(cneMobile))) {
		if (isNull($.trim(cnePhone))) {
			errorMsgs = errorMsgs + "Please enter the Consignor Phone.\n";
			if (isNull(focusObj)) {
				focusObj = "cnePhone";
			}

		}
	}

	if (!isNull(errorMsgs)) {
		alert(errorMsgs);
		setTimeout(function() {
			document.getElementById(focusObj).focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	return isValid;
}

function validateMandateForRateDox() {
	var consgNumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var isValid = true;

	if (isNull($.trim(consgNumber))) {
		alert("Please enter the consignment number");
		setTimeout(function() {
			document.getElementById("cnNumber").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull($.trim(pincode))) {
		alert("Please enter the Pincode");
		setTimeout(function() {
			document.getElementById("pinCode").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isEmptyWeight(finalWeight) || isNull(finalWeight)) {
		alert("Please Enter Actual Weight ");
		setTimeout(function() {
			document.getElementById("weightGm").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	return isValid;

}

function calcCNrateForFOC() {
	var consgNumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var consgType = "";
	var rateType = "CH";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var productCode = consgNumber.substring(4, 5);
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if (validateMandateForRateDox()) {
		showProcessing();
		url = './focBookingDox.do?submitName=calculateRateForConsignment&productCode='
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
				+ "&consgNumber=" + consgNumber;
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				printCallBackCNRateDetailsForFOC(req);
			}
		});
	}

}

function printCallBackCNRateDetailsForFOC(data) {
	document.getElementById("consgRateDtls").value = "";
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			document.fOCBookingDoxForm.action = "/udaan-web/focBookingDox.do?submitName=viewFOCBooking";
			document.fOCBookingDoxForm.submit();
		}
		{
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
			document.getElementById("consgRateDtls").value = rateDtlsFormat;
		}
	}
	jQuery.unblockUI();
}
