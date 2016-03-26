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

function loadDefaultObjectsEB() {
	document.getElementById('pincode').focus();
	}


/**
 * updates Deatils
 * 
 * @author uchauhan
 */
function updateDeatils() {
	if (checkMadatoryParam()) {
		showProcessing();
		var url = "/udaan-web/updateEBBooking.do?submitName=updateEBBookingsDtls";
		document.forms["emotionalBondBookingForm"].action = url;
		document.forms["emotionalBondBookingForm"].submit();
	}
}

/**
 * validates the pincode
 * 
 * @param pinObj
 *            contains the pincode
 * @author uchauhan
 */
function validatePincodeEB(pinObj) {
	if (pinObj.value != null && pinObj.value != "") {
		var bookingOfficeId = "";
		var pincode = pinObj.value;
		// var consgNumber = document.getElementById("cnNumber").value;
		var consgSeries = "E";

		if (pincode.length < 6) {
			alert("Invalid pincode");
			document.getElementById("pincode").value = "";
			document.getElementById("destCity").value = "";
			document.getElementById("cityId").value = "";
			document.getElementById("pincode").focus();
			return;
		}

		bookingOfficeId = document.getElementById("bookingOfficeId").value;

		url = './cashBooking.do?submitName=validatePincode&pincode=' + pincode
				+ "&bookingOfficeId=" + bookingOfficeId + "&consgSeries="
				+ consgSeries;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackEBPincode(req);
			}
		});
	}
}

/**
 * validates the mandatory Params
 * 
 * @returns {true} if the fileds are valid
 * @returns {false} if the fileds are invalid
 * @author uchauhan
 */
function checkMadatoryParam() {
	var isValid = true;
	var checkBoxes = document.emotionalBondBookingForm.checkBox;
	var isSelected = false;
	if (checkBoxes.length == undefined) {
		if (document.getElementById('checkBox1').checked) {
			isSelected = true;
			var status = document.getElementById('status1').value;
			if (isNull(status) || status == "B") {
				alert("Please select the status otherthan booked.");
				document.getElementById('status1').focus();
				return;
			}
		}
	} else {
		for ( var j = 1; j <= checkBoxes.length; j++) {
			if (document.getElementById('checkBox' + j).checked) {
				isSelected = true;
				var status = document.getElementById('status' + j).value;
				if (isNull(status) || status == "B") {
					alert("Please select the status other than booked.");
					document.getElementById('status' + j).focus();
					return;
				}
			}
		}
	}

	if (!isSelected) {
		alert("Please select any consignment(s) for update.");
		isValid = false;
		return isValid;
	}
	return isValid;

}

/**
 * gets the booking details for selected date
 * 
 * @author uchauhan
 */
function getBABookingsDtls() {
	showProcessing();
	var url = "/udaan-web/updateEBBooking.do?submitName=getEBBookingsDtls";
	document.forms["emotionalBondBookingForm"].action = url;
	document.forms["emotionalBondBookingForm"].submit();
}

function getEBBookingsDtls() {
	var dlvDateTime=document.getElementById("dlvDateTime").value;
	if(!isNull(dlvDateTime)){
	showProcessing();
	var url = "/udaan-web/updateEBBooking.do?submitName=getEBBookingsDtls";
	document.forms["emotionalBondBookingForm"].action = url;
	document.forms["emotionalBondBookingForm"].submit();
	}else{
		alert("Please Select a valid Date.");
		setTimeout(function() {
			document.getElementById("dlvDateTime").focus();
		}, 10);
		return false;
	}
}

/**
 * printCallBackEBPincode
 * 
 * @param data
 * @author uchauhan
 */
function printCallBackEBPincode(data) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isValidPincode == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("pincode").value = "";
				document.getElementById("pincode").focus();
				document.getElementById("destCity").value = "";
				document.getElementById("cityId").value = "";
			} else if (cnValidation.isValidPriorityPincode == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("pincode").value = "";
				document.getElementById("pincode").focus();
				document.getElementById("destCity").value = "";
				document.getElementById("cityId").value = "";
			} else {
				document.getElementById("destCity").value = cnValidation.cityName;
				document.getElementById("cityId").value = cnValidation.cityId;
				document.getElementById("pincodeId").value = cnValidation.pincodeId;

			}
		}
	}

}

/**
 * populates the screen with initial values
 * 
 * @author uchauhan
 */
function viewBooking() {
	var bookingType = document.getElementById('bookingType').value;
	document.emotionalBondBookingForm.action = "./emotionalBondBooking.do?submitName=getEBBookingsDtls&bookingType="
			+ bookingType;
	document.emotionalBondBookingForm.submit();
}

/**
 * gets the price for selected items
 * 
 * @param obj
 * @param prefName
 *            list of selected items
 * @author uchauhan
 */
function getPrice1(obj, prefName) {
	if (prefName != "Others") {
		document.getElementById("amount").readOnly = true;
		var preferenceIds = getCheckBoxValues();
		if (!isNull(preferenceIds)) {
			url = './emotionalBondBooking.do?submitName=getEmotionalBondRateDetails&preferenceIds='
					+ preferenceIds;
			ajaxCallWithoutForm(url, getPriceResponse);
		}
	} else {
		document.getElementById("amount").readOnly = false;
	}

}

/**
 * populates the price and amount
 * 
 * @param data
 * @author uchauhan
 */
function getPriceResponse(data) {
	// var price = document.getElementById('price').value;
	var finalPrice = data.finalPrice;
	finalPrice = parseInt(finalPrice);
	/*
	 * if (price != "") { price = parseInt(price); finalPrice = price +
	 * finalPrice; }
	 */

	document.getElementById('price').value = finalPrice;
	document.getElementById('amount').value = finalPrice;
}

var selectedObj = null;
function getPrice(obj, prefName) {
	selectedObj = obj;
	// var consgNumber = document.getElementById('cnNumber').value;

	var pincode = document.getElementById('pincode').value;
	// alert("pincode:" + pincode);
	var consgType = "PPX";
	var rateType = "CH";
	var productCode = "E";
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	// alert("originOfficeId:" + originOfficeId);
	if (prefName != "Others") {
		document.getElementById("amount").readOnly = true;
		// alert("amount: " + document.getElementById("amount"));
		var preferenceIds = getCheckBoxValues();
		if (!isNull(pincode)) {
			showProcessing();
			var prefName = selectedObj.value;
			var prefStatus = selectedObj.checked;
			url = './emotionalBondBooking.do?submitName=calcRateForEmotionalBondBooking&productCode='
					+ productCode
					+ "&consgType="
					+ consgType
					+ "&destPincode="
					+ pincode
					+ "&rateType="
					+ rateType
					+ "&bookingOfficeId="
					+ originOfficeId
					+ "&preferenceIds="
					+ preferenceIds
					+ "&currentPrefName="
					+ prefName
					+ "&currentPrefStatus="
					+ prefStatus;
			jQuery.ajax({
				url : url,
				type : "POST",
				success : function(req) {
					printCallBackCNRateDetails(req);
				}
			});
		}
	} else {
		document.getElementById("amount").readOnly = false;
	}

}

function printCallBackCNRateDetails(data) {
	jQuery.unblockUI();
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			// document.emotionalBondBookingForm.action =
			// "/udaan-web/emotionalBondBooking.do?submitName=viewEmotionalBondBooking";
			// document.emotionalBondBookingForm.submit();
			if (!isNull(selectedObj)) {
				selectedObj.checked = false;
			}
		} else {
			document.getElementById("price").value = convertToFraction(
					cnRateDetails.finalPrice, 2);
			// document.getElementById("cnPrice").value =
			// convertToFraction(cnRateDetails.finalPrice, 2);
			// finalRate = convertToFraction(cnRateDetails.finalPrice, 2);
		}
	}

}

/**
 * checks the Date
 * 
 * @returns {true} if future date
 * @returns {false} if current or past date
 * @author uchauhan
 */
function checkDate() {
	var dlvDate = document.getElementById('dlvDateTime').value;
	/*
	 * if(dlvDate== null || dlvDate == ""){ alert('Please select delivery
	 * date'); return false; }
	 */
	if (!isNull(dlvDate)) {
		if (!isFutureDate(dlvDate)) {
			alert("Only future date allowed");
			document.getElementById('dlvDateTime').value = "";
			setTimeout(function() {
				document.getElementById("dlvDateTime").focus();
			}, 10);
			return false;
		}
	}
}

/**
 * check Format of the time
 * 
 * @param obj
 * @returns {true} if 24 hr format
 * @returns {false} if not in 24 hr format
 * @author uchauhan
 */
function chckFormat(obj) {
	if (obj != null) {
		var format = new RegExp("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");
		if (!document.emotionalBondBookingForm.time.value.match(format)) {
			alert("Please enter time in HH:MM 24 hour format");
			document.getElementById("time").value = "";
			setTimeout(function() {
				document.getElementById("time").focus();
			}, 10);
			return false;
		} else {
			var date = document.getElementById('dlvDateTime').value;
			var dateTime = obj.value;
			document.getElementById('deliveryDateTime').value = date + " "
					+ dateTime;
		}
	}
}

/**
 * validates the amount
 * 
 * @param obj
 *            is the amount
 * @returns {false} if amount is less than price
 * @returns {true} if amount is greater than price
 * @author uchauhan
 */
function isValidAmount() {
	var amount = document.getElementById('amount').value;
	var price = document.getElementById('price').value;
	amount = parseFloat(amount);
	price = parseFloat(price);
	if (amount < price) {
		alert(" Amount Can not be less than the price");
		document.getElementById('amount').value = "";
		setTimeout(function() {
			document.getElementById("amount").focus();
		}, 10);
		return false;
	}

}

/**
 * validates the mandatory fields
 * 
 * @returns {true} if the fields are valid
 * @returns {false} if the fields are invalid
 * @author uchauhan
 */
function validateMandatoryFields() {
	var isValid = true;
	// var CNnumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pincode').value;
	var dest = document.getElementById('destCity').value;

	var cnrName = document.getElementById('cnrName').value;
	var cnrMobile = document.getElementById('cnrMobile').value;
	var cnrPhone = document.getElementById('cnrPhone').value;
	var cneName = document.getElementById('cneName').value;
	var cneMobile = document.getElementById('cneMobile').value;
	var cnePhone = document.getElementById('cnePhone').value;
	var relation = document.getElementById('relation').value;
	var email = document.getElementById('email').value;
	var date = document.getElementById('dlvDateTime').value;
	var time = document.getElementById('time').value;
	var amount = document.getElementById('amount').value;
	var price = document.getElementById('price').value;

	if (pincode == null || pincode == "") {
		alert("Please enter the Pincode.");
		setTimeout(function() {
			document.getElementById("pincode").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (date == null || date == "") {
		alert("Please select the delvery date.");
		setTimeout(function() {
			document.getElementById("dlvDateTime").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (time == null || time == "") {
		alert("Please enter the delvery time.");
		setTimeout(function() {
			document.getElementById("time").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (dest == null || dest == "") {
		alert("Please enter the Destinatiom.");
		setTimeout(function() {
			document.getElementById("dest").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (cnrName == null || cnrName == "") {

		alert("Please enter consignor name.");
		setTimeout(function() {
			document.getElementById("cnrName").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (cnrMobile == null || cnrMobile == "") {
		if (cnrPhone == null || cnrPhone == "") {
			alert("Please enter consignor mobile/phone number.");
			setTimeout(function() {
				document.getElementById("cnrMobile").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (cnrPhone == null || cnrPhone == "") {
		if (cnrMobile == null || cnrMobile == "") {
			alert("Please enter consignor mobile/phone number.");
			setTimeout(function() {
				document.getElementById("cnrPhone").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (cneName == null || cneName == "") {
		alert("Please enter consignee name.");
		setTimeout(function() {
			document.getElementById("cneName").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (cneMobile == null || cneMobile == "") {
		if (cnePhone == null || cnePhone == "") {
			alert("Please enter consignee mobile/phone number.");
			setTimeout(function() {
				document.getElementById("cneMobile").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}
	if (cnePhone == null || cnePhone == "") {
		if (cneMobile == null || cneMobile == "") {
			alert("Please enter consignee mobile/phone number.");
			setTimeout(function() {
				document.getElementById("cnePhone").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (email == null || email == "") {
		alert("Please enter email id.");
		setTimeout(function() {
			document.getElementById("email").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (relation == null || relation == "") {
		alert("Please enter relationship.");
		setTimeout(function() {
			document.getElementById("relation").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	var chkBoxValues = getCheckBoxValues();
	var chkBoxLength = 0;
	chkBoxLength = chkBoxValues.length;
	if (chkBoxLength > 0) {

	} else {
		alert("Please select any felicitation preference.");
		isValid = false;
		return isValid;
	}
	
	if (isNull(amount) && isNull(price)) {
		alert("EB booking price/amount cannot be zero");
		isValid = false;
		return isValid;
	}

	return isValid;
}

/**
 * saves the data
 * 
 * @author uchauhan
 */
function saveEmotionalBondBooking() {
	if (validateMandatoryFields()) {
		showProcessing();
		var chkBoxValues = getCheckBoxValues();
		var chkBoxLength = 0;
		chkBoxLength = chkBoxValues.length;
		if (chkBoxLength > 0) {
			document.getElementById('preferenceIds').value = chkBoxValues;
			if (!isNull(document.getElementById('preferenceIds').value)) {
				url = './emotionalBondBooking.do?submitName=saveEmotionalBondBooking';
				ajaxCall(url, "emotionalBondBookingForm",
						saveEmotionalBondBookingResponse);
			}
		} else {
			alert("Please select any felicitation preference.");
		}
	}
}

/**
 * displays the error/success msg after save
 * 
 * @param data
 * @author uchauhan
 */
function saveEmotionalBondBookingResponse(data) {
	if (!isNull(data)) {
		var consgNumber = "";
		var msg = "";
		var resultArray = new Array();
		resultArray = data.split("#");
		consgNumber = resultArray[1];
		msg = resultArray[0];
		if (msg == "SUCCESS") {
			document.getElementById("cnNumber").value = consgNumber;
			alert("Consignment (" + consgNumber + ") booked successfully.");
		} else if (msg == "FAILURE") {
			alert("Exception occurred. Consignment not booked.");
		}
	}

	document.emotionalBondBookingForm.action = "/udaan-web/emotionalBondBooking.do?submitName=viewEmotionalBondBooking";
	document.emotionalBondBookingForm.submit();
	jQuery.unblockUI();

}

/**
 * clears the screen
 * 
 * @author uchauhan
 */
function clearDetails() {
	var flag = confirm("Do you want to clear the details ?");
	if (flag) {
		document.emotionalBondBookingForm.action = "/udaan-web/emotionalBondBooking.do?submitName=viewEmotionalBondBooking";
		document.emotionalBondBookingForm.submit();
	}
}

/**
 * clears the view emotional bond booking screen
 * 
 * @author uchauhan
 */
function clearEBViewDetails() {
	var flag = confirm("Do you want to clear the details ?");
	if (flag) {
		document.emotionalBondBookingForm.action = "/udaan-web/emotionalBondBooking.do?submitName=viewBookingsDtls";
		document.emotionalBondBookingForm.submit();
	}
}

/**
 * getCheckBoxValues
 * 
 * @returns {String}
 * @author uchauhan
 */
function getCheckBoxValues() {
	var chkValue = "";
	var checkBoxes = document.emotionalBondBookingForm.checkBox;
	if (checkBoxes.length == undefined) {
		if (document.getElementById('checkBox').checked) {
			chkValue = document.getElementById('checkBox').value;
		}
	} else {
		for ( var i = 0; i < checkBoxes.length; i++) {
			var curObj = document.getElementsByName('checkBox')[i];
			if (!isNull(curObj) && curObj.checked) {
				if (chkValue == null || chkValue == '') {
					chkValue = curObj.value;
				} else {
					chkValue = chkValue + "," + curObj.value;
				}
			}
		}
	}
	return chkValue;
}

/**
 * fnIsChecked
 * 
 * @param obj
 * @param count
 * @author uchauhan
 */
function fnIsChecked(obj, count, destBranchId) {
	var bookingOffId = document.getElementById("bookingOfficeId").value;
	if (bookingOffId != destBranchId) {
		alert("You are not authorized to change the status..");
		document.getElementById("isChecked" + count).value = "N";
		obj.checked = false;
	} else {
		if (obj.checked) {
			document.getElementById("isChecked" + count).value = "Y";
		} else {
			document.getElementById("isChecked" + count).value = "N";
		}
	}
}

function checkPrefSelected(obj) {
	if (obj != null) {
		var chkBoxValues = getCheckBoxValues();
		var chkBoxLength = 0;
		chkBoxLength = chkBoxValues.length;
		if (chkBoxLength > 0) {

		} else {
			alert("Please select any felicitation preference.");
		}

		if (!isNull(obj.value)) {
			document.getElementById("amount").readOnly = false;
		}
	}
}
