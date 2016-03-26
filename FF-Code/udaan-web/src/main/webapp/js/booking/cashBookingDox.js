/*Save function for Cash Booking Dox*/
/**
 * saveOrUpdateSaveBookingDox
 * 
 * @author sdalli
 */
function saveOrUpdateSaveBookingDox() {
	if (validateBookingFields()) {
		enablePaymentMode();
		var priorityService = jQuery('#prioritySelect option:selected').text();
		if (priorityService != "---Select---") {
			document.getElementById('priorityServiced').value = priorityService;
		}

		showProcessing();
		url = './cashBooking.do?submitName=saveOrUpdateCashBookingDox';
		jQuery.ajax({
			url : url,
			data : jQuery("#cashBookingDoxForm").serialize(),
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
	var bookRes = new Array();
	bookRes = data.split("#");
	if (bookRes[0] == "SUCCESS") {
		alert(bookRes[1]);
		if (confirm("Do You want to print?")) {
			printCashBookingDox();
		} 
	} else if (bookRes[0] == "FAILURE") {
		alert(bookRes[1]);
	}
	document.cashBookingDoxForm.action = "/udaan-web/cashBooking.do?submitName=createCashBookingDox";
	document.cashBookingDoxForm.submit();
	jQuery.unblockUI();
}

/**
 * calcCNrate
 * 
 * @author sdalli
 */
function calculatePriorityRate() {
	var priorityService = jQuery('#prioritySelect option:selected').text();
	if (priorityService != "---Select---") {
		calcCNrate();
	}
}

function calcCNrate() {
	var consgNumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var discountOnCN = document.getElementById('discount').value;
	var splChg = document.getElementById('inputSplChg').value;

	var priorityService = jQuery('#prioritySelect option:selected').text();

	var consgType = "";
	var rateType = "CH";
	var discount = "";
	var splCharges = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var productCode = consgNumber.substring(4, 5);
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if (!isNull(discountOnCN))
		discount = discountOnCN;
	if (!isNull(splChg))
		splCharges = splChg;
	if (!isNull(consgNumber) && !isNull(pincode) && !isEmptyWeight(finalWeight)  && !isNaN(finalWeight) && isValidPincode==true) {
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
				+ "&discount="
				+ discount
				+ "&splCharges="
				+ splCharges
				+ "&bookingOfficeId="
				+ originOfficeId
				+ "&priorityService="
				+ priorityService
				+ "&consgNumber=" + consgNumber;
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				printCallBackCNRateDetails(req);
			}
		});
	}
}

function validateMandateForRateDOX() {
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

	/*else if (isNull($.trim(pincode)) || isValidPincode==false) {
		alert("Please enter the Pincode");
		setTimeout(function() {
			document.getElementById("pinCode").focus();
		}, 10);
		isValid = false;
		return isValid;
	}*/

	else if (isEmptyWeight(finalWeight) || isNull(finalWeight) || isNaN(finalWeight)) {
		alert("Please Enter valid  Actual Weight ");
		setTimeout(function() {
			document.getElementById("weightGm").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	
	return isValid;
}
function printCallBackCNRateDetails(data) {
	document.getElementById("consgRateDtls").value = "";
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			document.cashBookingDoxForm.action = "/udaan-web/cashBooking.do?submitName=createCashBookingDox";
			document.cashBookingDoxForm.submit();
		} else {
			document.getElementById("fuelChg").value="";
			document.getElementById("riskChg").value="";
			document.getElementById("tpPayChg").value="";
			document.getElementById("airportHadlChg").value="";
			document.getElementById("splChg").value="";
			document.getElementById("serviceTax").value="";
			document.getElementById("eduCessChg").value="";
			document.getElementById("higherEduCessChg").value="";
			document.getElementById("freightChg").value="";
			document.getElementById("finalCNPrice").value="";
			document.getElementById("cnPrice").value="";
			if (cnRateDetails.fuelChg != null && cnRateDetails.fuelChg > 0)
				document.getElementById("fuelChg").value = convertToFraction(
						cnRateDetails.fuelChg, 2);
			if (cnRateDetails.riskSurChg != null
					&& cnRateDetails.riskSurChg > 0)
				document.getElementById("riskChg").value = convertToFraction(
						cnRateDetails.riskSurChg, 2);
			if (cnRateDetails.topayChg != null && cnRateDetails.topayChg > 0)
				document.getElementById("tpPayChg").value = convertToFraction(
						cnRateDetails.topayChg, 2);
			if (cnRateDetails.airportHandlingChg != null
					&& cnRateDetails.airportHandlingChg > 0)
				document.getElementById("airportHadlChg").value = convertToFraction(
						cnRateDetails.airportHandlingChg, 2);
			if (cnRateDetails.splChg != null && cnRateDetails.splChg > 0)
				document.getElementById("splChg").value = convertToFraction(
						cnRateDetails.splChg, 2);
			if (cnRateDetails.serviceTax != null
					&& cnRateDetails.serviceTax > 0)
				document.getElementById("serviceTax").value = convertToFraction(
						cnRateDetails.serviceTax, 2);
			if (cnRateDetails.eduCessChg != null
					&& cnRateDetails.eduCessChg > 0)
				document.getElementById("eduCessChg").value = convertToFraction(
						cnRateDetails.eduCessChg, 2);
			if (cnRateDetails.higherEduCessChg != null
					&& cnRateDetails.higherEduCessChg > 0)
				document.getElementById("higherEduCessChg").value = convertToFraction(
						cnRateDetails.higherEduCessChg, 2);
			if (cnRateDetails.freightChg != null
					&& cnRateDetails.freightChg > 0)
				document.getElementById("freightChg").value = convertToFraction(
						cnRateDetails.freightChg, 2);
			if (cnRateDetails.finalPrice != null
					&& cnRateDetails.finalPrice > 0) {
				document.getElementById("finalCNPrice").value = convertToFraction(
						cnRateDetails.finalPrice, 2);
				document.getElementById("cnPrice").value = convertToFraction(
						cnRateDetails.finalPrice, 2);
				finalRate = convertToFraction(cnRateDetails.finalPrice, 2);
			}

		}
	}
	jQuery.unblockUI();
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

/* Validating mandatery Fields on submit button */
/**
 * validateBookingFields
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function validateBookingFields() {
	var isValid = true;
	var cnPrice = document.getElementById('cnPrice').value;
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
	var prioritySelect = document.getElementById('prioritySelect').value;
	var consgSeries = CNnumber.substring(4, 5);
	var errorMsgs = "";
	var focusObj = "";
	if (cnPrice == null || cnPrice == "") {
		document.getElementById("cnPrice").value="";
		errorMsgs = "Price Cannot be Empty.\n";
		focusObj = "cnPrice";
	}
	if (isNull($.trim(CNnumber))) {
		document.getElementById("cnNumber").value="";
		errorMsgs = "Please enter the CN Number.\n";
		focusObj = "cnNumber";
		/*
		 * alert("Please enter the CN Number."); setTimeout(function() {
		 * document.getElementById("cnNumber").focus(); }, 10); isValid = false;
		 * return isValid;
		 */
	}
	if (isNull($.trim(pincode))) {
		document.getElementById("pinCode").value="";
		errorMsgs = errorMsgs + "Please enter the Pincode.\n";
		if (isNull(focusObj)) {
			focusObj = "pinCode";
		}
		/*
		 * alert("Please enter the Pincode."); setTimeout(function() {
		 * document.getElementById("pinCode").focus(); }, 10); isValid = false;
		 * return isValid;
		 */
	}
	if (isNull($.trim(dest))) {
		document.getElementById("destCity").value="";
		errorMsgs = errorMsgs + "Please enter the Destination.\n";
		if (isNull(focusObj)) {
			focusObj = "destCity";
		}
		/*
		 * alert("Please enter the Destinatiom."); setTimeout(function() {
		 * document.getElementById("dest").focus(); }, 10); isValid = false;
		 * return isValid;
		 */
	}
	if (consgSeries == "P" || consgSeries == "p") {
		if (isNull(prioritySelect)) {
			errorMsgs = errorMsgs + "Please select the Priortiy Service.\n";
			if (isNull(focusObj)) {
				focusObj = "prioritySelect";
			}
			/*
			 * alert("Please select the Priortiy Service");
			 * setTimeout(function() {
			 * document.getElementById("prioritySelect").focus(); }, 10);
			 * isValid = false; return isValid;
			 */
		}
	}

	if (isEmptyWeight(finalWeight) || isNaN(finalWeight)) {
		errorMsgs = errorMsgs + "Please enter the Weight.\n";
		if (isNull(focusObj)) {
			focusObj = "weightGm";
		}
		// alert("Please enter the Weight.");
		document.getElementById("weightGm").value = "";
		document.getElementById("weight").value = "";
		/*
		 * setTimeout(function() { document.getElementById("weightGm").focus(); },
		 * 10); isValid = false; return isValid;
		 */
	}

	if (isNull($.trim(cnrName))) {
		document.getElementById("cnrName").value="";
		errorMsgs = errorMsgs + "Please enter the Consignor Name.\n";
		if (isNull(focusObj)) {
			focusObj = "cnrName";
		}
		/*
		 * alert("Please enter the Consignor Name."); setTimeout(function() {
		 * document.getElementById("cnrName").focus(); }, 10); isValid = false;
		 * return isValid;
		 */
	}

	if (isNull($.trim(cneName))) {
		document.getElementById("cneName").value="";
		errorMsgs = errorMsgs + "Please enter the Consignee Name.\n";
		if (isNull(focusObj)) {
			focusObj = "cneName";
		}
		/*
		 * alert("Please enter the Consignee Name."); setTimeout(function() {
		 * document.getElementById("cneName").focus(); }, 10); isValid = false;
		 * return isValid;
		 */
	}

	var discountVal = document.getElementById('discount').value;
	if (discountVal != null && discountVal != "") {
		if (document.getElementById("approver").selectedIndex == 0) {
			errorMsgs = errorMsgs + "Please select the Approver's name.\n";
			if (isNull(focusObj)) {
				focusObj = "approver";
			}
			/*
			 * alert("Please select the Approver's name"); setTimeout(function() {
			 * document.getElementById("approver").focus(); }, 10); isValid =
			 * false; return isValid;
			 */
		}
	}

	var paymentMode = document.getElementById('payMode').value;
	var payMode1 = paymentMode.split("#")[1];

	if (payMode1 == "CHQ") {

		var bank = document.getElementById('bank').value;
		var bankBranchName = document.getElementById('bankBranchName').value;
		var chequeDate = document.getElementById('chequeDate').value;
		var cno = document.getElementById('cno').value;

		if (isNull($.trim(bank))) {
			document.getElementById("bank").value="";
			errorMsgs = errorMsgs + "Please enter the Bank Name.\n";
			if (isNull(focusObj)) {
				focusObj = "bank";
			}
			/*
			 * alert("Please enter the Bank Name."); setTimeout(function() {
			 * document.getElementById("bank").focus(); }, 10); isValid = false;
			 * return isValid;
			 */
		}
		if (isNull($.trim(bankBranchName))) {
			document.getElementById("bankBranchName").value="";
			errorMsgs = errorMsgs + "Please enter the name of Bank's Branch.\n";
			if (isNull(focusObj)) {
				focusObj = "bankBranchName";
			}
			/*
			 * alert("Please enter the name of Bank's Branch.");
			 * setTimeout(function() {
			 * document.getElementById("bankBranchName").focus(); }, 10);
			 * isValid = false; return isValid;
			 */
		}
		if (chequeDate == null || chequeDate == "") {
			errorMsgs = errorMsgs + "Please enter the cheque Date.\n";
			if (isNull(focusObj)) {
				focusObj = "chequeDate";
			}
			/*
			 * alert("Please enter the cheque Date."); setTimeout(function() {
			 * document.getElementById("chequeDate").focus(); }, 10); isValid =
			 * false; return isValid;
			 */
		}

		if (isNull($.trim(cno))) {
			document.getElementById("cno").value="";
			errorMsgs = errorMsgs + "Please enter the cheque Number.\n";
			if (isNull(focusObj)) {
				focusObj = "cno";
			}
			/*
			 * alert("Please enter the cheque Number."); setTimeout(function() {
			 * document.getElementById("cno").focus(); }, 10); isValid = false;
			 * return isValid;
			 */

		}
	}

	else if (payMode1 == "PVC") {

		var pcd = document.getElementById('privilegeCardNo').value;
		var pcdAmt = document.getElementById('privilegeCardAmt').value;

		if (isNull($.trim(pcd))) {
			errorMsgs = errorMsgs + "Please enter the Privilege Card Number.\n";
			if (isNull(focusObj)) {
				focusObj = "privilegeCardNo";
			}
			/*
			 * alert("Please enter the Privilege Card Number.");
			 * setTimeout(function() {
			 * document.getElementById("privilegeCardNo").focus(); }, 10);
			 * isValid = false; return isValid;
			 */

		}
		if (isNull($.trim(pcdAmt))) {
			errorMsgs = errorMsgs + "Please enter the Privilege Amount.\n";
			if (isNull(focusObj)) {
				focusObj = "privilegeCardAmt";
			}
			/*
			 * alert("Please enter the Privilege Amount.");
			 * setTimeout(function() {
			 * document.getElementById("privilegeCardAmt").focus(); }, 10);
			 * isValid = false; return isValid;
			 */

		}
	}
	if (isNull($.trim(cnrMobile)) && isNull($.trim(cnrPhone))) {
		// alert("Please enter the Consignors Mobile or Phone Number");
		errorMsgs = errorMsgs
				+ "Please enter the Consignors Mobile or Phone Number.\n";
		if (isNull(focusObj)) {
			focusObj = "cneMobile";
		}
	}

	if (isNull($.trim(cneMobile)) && isNull($.trim(cnePhone))) {
		// alert("Please enter the Consignees Mobile or Phone Number");
		errorMsgs = errorMsgs
				+ "Please enter the Consignees Mobile or Phone Number.\n";
		if (isNull(focusObj)) {
			focusObj = "cnrMobile";
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

/* Cancel Function for Cash Booking Dox */
/**
 * cancelBookingDetails
 * 
 * @author sdalli
 */
function cancelBookingDetails() {

	if (promptConfirmation("cancel")) {
		document.cashBookingDoxForm.action = "/udaan-web/cashBooking.do?submitName=createCashBookingDox";
		document.cashBookingDoxForm.submit();
	}
}

function validateConsignmentCashDox(consgNumberObj) {
	var consgNumber = document.getElementById("cnNumber").value;
	if (!isNull(consgNumber) && !isNull(oldConsingment)
			&& consgNumber != oldConsingment) {
		var confirm = window
				.confirm("Header details have been changed, do you clear the entered consignment data?");
		if (confirm) {
			document.cashBookingDoxForm.action = "/udaan-web/cashBooking.do?submitName=createCashBookingDox";
			document.cashBookingDoxForm.submit();
		}
		else {
			 document.getElementById("cnNumber").value = oldConsingment;
		}
	}
	else {
		validateConsignment(consgNumberObj);
	}
}

function printCashBookingDox() {

	var consgNumber = $("#cnNumber").val();
	url = './cashBooking.do?submitName=printCashBooking&consgNumber='
			+ consgNumber;
	window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left= 412,top = 184,scrollbars=yes');
	/*
	 * document.cashBookingDoxForm.action = url;
	 * document.cashBookingDoxForm.submit();
	 */

}

function onlyNumberAndEnterKeyNavForCashDox(e, Obj, focusId){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13 && validateWeightForDOX(e)) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
	
}


function validateWeightForDOX(e) {
	weightFormatForGm();
	var gmValue = document.getElementById("weightGm").value;
	var kgValue = document.getElementById("weight").value;
	var actualWt = document.getElementById("finalWeight").value;

	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which;

	if (isNaN(actualWt)) {
		actualWt = "0.000";
	}
	actualWt = parseFloat(actualWt).toFixed(3);
	if (isEmptyWeight(actualWt)) {
		if (key == 13) {
			alert("Please Enter Valid Weight.");
			setTimeout(function() {
				document.getElementById("weightGm").focus();
			}, 10);

		} else {
			return true;
		}
	} else {
		return true;
	}

}