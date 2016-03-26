/** The MIN_DEC_VAL */
var MIN_DEC_VAL = 0;
/** The DEC_VAL */
var DEC_VAL = 0;
/** The IS_POLICY_MANDATORY */
var IS_POLICY_MANDATORY = "";
var MAX_DECLARED_VALUE = 100000;
/**
 * loadDefaultObjects
 * 
 * @author sdalli
 */
function loadDefaultObjects() {
	getApprovers();
	getWeightFromWeighingMachine();
	document.getElementById("approver").focus();

}

/**
 * validateFocBookingParcelFields
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function validateFocBookingParcelFields() {
	var isValid = true;
	var CNnumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var dest = document.getElementById('destCity').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var actualWeight = document.getElementById('actualWeight').value;
	var cnrName = document.getElementById('cnrName').value;
	var cnrMobile = document.getElementById('cnrMobile').value;
	var cnrPhone = document.getElementById('cnrPhone').value;
	var cneName = document.getElementById('cneName').value;
	var cneMobile = document.getElementById('cneMobile').value;
	var cnePhone = document.getElementById('cnePhone').value;
	var declaredValue = document.getElementById('declaredValue').value;
	var noOfPcs = document.getElementById('pieces');
	var approver = document.getElementById('approver').value;
	var content = document.getElementById("contentName");
	var cnContentOther = document.getElementById("cnContentOther");
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
	if (isEmptyWeight(finalWeight) && isEmptyWeight(actualWeight)) {
		document.getElementById("weightGm").value = "";
		document.getElementById("weight").value = "";

		errorMsgs = errorMsgs + "Please enter the Weight.\n";
		if (isNull(focusObj)) {
			focusObj = "weight";
		}
	}

	if (!isNull(noOfPcs) && isNull($.trim(noOfPcs.value))) {
		errorMsgs = errorMsgs + "Please enter the valid No Of Pieces.\n";
		if (isNull(focusObj)) {
			focusObj = "pieces";
		}

	} else if (noOfPcs > 1) {
		var childCNDetails = document.getElementById("details").value;
		if (isNull(childCNDetails)) {
			alert("Please capture child consignment details ");
			setTimeout(function() {
				document.getElementById("pieces").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
	var contentCode = "";
	var cnContet = content.value;
	if (!isNull(cnContet)) {
		contentCode = cnContet.split("#")[1];
	} else {
		
		errorMsgs = errorMsgs + "Please select consignment content.\n";
		if (isNull(focusObj)) {
			focusObj = "content";
		}
	

	}
	if (!isNull(contentCode) && contentCode == '999') {
		if (isNull($.trim(cnContentOther.value))) {
			document.getElementById("cnContentOther").value="";
			errorMsgs = errorMsgs + "Please Enter Other consignment content.\n";
			if (isNull(focusObj)) {
				focusObj = "cnContentOther";
			}
		}
	}

	if (isNull($.trim(declaredValue))) {
		errorMsgs = errorMsgs + "Please enter the Declared Value.\n";
		if (isNull(focusObj)) {
			focusObj = "declaredValue";
		}

	}

	var paperWork = document.getElementById("paperWork");
	var prdValue = paperWork.options[paperWork.selectedIndex].value;

	if (paperWork.options.length > 1) {
		if (isNull(prdValue)) {
			errorMsgs = errorMsgs + "Please Select the Paperworks.\n";
			if (isNull(focusObj)) {
				focusObj = "paperWork";
			}
		}
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
			document.getElementById("cnrMobile").value="";
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

/**
 * saveOrUpdateSaveFOCBookingParcel
 * 
 * @author sdalli
 */
function saveOrUpdateSaveFOCBookingParcel() {
	if (validateFocBookingParcelFields()) {
		showProcessing();
		url = './focBookingParcel.do?submitName=saveOrUpdateFocBookingParcel';
		ajaxCall(url, "fOCBookingParcelForm", printCallBackSaveFocBookParcel);
	}
}

/**
 * printCallBackSaveFocBookParcel
 * 
 * @param data
 * @author sdalli
 */
function printCallBackSaveFocBookParcel(data) {
	if (data != null && data != "") {
		var bookRes = new Array();
		bookRes = data.split("#");
		if (bookRes[0] == "SUCCESS") {
			alert(bookRes[1]);
			if (confirm("Do You want to print?")) {
				printFOCBookingParcel();
			}
		} else if (bookRes[0] == "FAILURE") {
			alert(bookRes[1]);
		}
		redirectPage();
		jQuery.unblockUI();
	}
}

/*
 * ******Start******** Print parcel code
 */
function printFOCBookingParcel() {
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
 * ******End******** Print parcel code
 */

/**
 * cancelfocBookingParcelDetails
 * 
 * @author sdalli
 */
function cancelfocBookingParcelDetails() {
	if (promptConfirmation("cancel")) {
		var docType = "PPX";
		document.fOCBookingParcelForm.action = "/udaan-web/focBookingParcel.do?submitName=viewFOCBookingParcel&docType="
				+ docType;
		document.fOCBookingParcelForm.submit();
	}
}

/* Enter key navigation */
function callEnterKeyInContentFOC(e, contentCode) {
	var cntcode = document.getElementById("contentCode").value;
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		if (cntcode == '999') {
			document.getElementById("cnContentOther").focus();
		} else {
			document.getElementById("declaredValue").focus();
		}
		return false;
	}
}

function isValidDecValueFOC(obj) {
	var bookingType = document.getElementById("bookingType").value;
	var decVal = obj.value;
	var decValue = parseFloat(decVal);
	if (decValue == "0" || isNull(decVal)) {
		alert("Please enter a non-zero declared value.");
		obj.value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
	url = './baBookingParcel.do?submitName=validateDeclaredvalue&declaredVal='
			+ decVal + '&bookingType=' + bookingType;
	ajaxCallWithoutForm(url, setBookingDtlsResponse);
}

function setBookingDtlsResponse(data) {
	if (!isNull(data)) {
		if (data.isValidDeclaredVal == "N") {
			alert('Declared Value Max limit exceeded.');
			document.getElementById("declaredValue").value = "";
			setTimeout(function() {
				document.getElementById("declaredValue").focus();
			}, 10);
		} else {
			calcCNrateForFOCParcel();
			getPaperWorks();
			getInsuarnceConfigDtls();
		}
	}
}

function validateMandateForRateParcel() {
	var consgNumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var declaredValue = document.getElementById('declaredValue').value;
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
			document.getElementById("weight").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull($.trim(declaredValue))) {
		alert("Please enter the declared value");
		setTimeout(function() {
			document.getElementById("declaredValue").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	return isValid;
}
function calcCNrateForFOCParcel() {
	var consgType = "";
	var rateType = "CH";
	var splCharges = "";
	var consgNumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var declaredValue = document.getElementById('declaredValue').value;
	var insuredBy = document.getElementById('insuaranceNo').value;
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var productCode = consgNumber.substring(4, 5);
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if (validateMandateForRateParcel()) {
		showProcessing();
		url = './cashBooking.do?submitName=calculateRateForConsignment&productCode='
				+ productCode
				+ "&consgType="
				+ consgType
				+ "&destPincode="
				+ pincode
				+ "&consgWeight="
				+ finalWeight
				+ "&rateType="
				+ rateType
				+ "&declaredValue="
				+ declaredValue
				+ "&splCharges="
				+ splCharges
				+ "&bookingOfficeId="
				+ originOfficeId
				+ "&declaredValue="
				+ declaredValue
				+ "&insuredBy="
				+ insuredBy
				+ "&consgNumber=" + consgNumber;
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				printCallBackCNRateDetailsForFOCParcel(req);
			}
		});
	}
}

function printCallBackCNRateDetailsForFOCParcel(data) {
	document.getElementById("consgRateDtls").value = "";
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			var docType = "PPX";
			document.fOCBookingParcelForm.action = "/udaan-web/focBookingParcel.do?submitName=viewFOCBookingParcel&docType="
					+ docType;
			document.fOCBookingParcelForm.submit();
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
