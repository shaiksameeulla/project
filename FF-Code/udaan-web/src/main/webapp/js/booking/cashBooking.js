var finalRate=0.00;
var oldConsingment="";
var isValidPincode=false;

/**
 * ` * loadDefaultObjects
 * 
 * @author sdalli
 */
function loadDefaultObjects() {
	// Getting weight from weighing scale
	getWeightFromWeighingMachine();
	getApprovers();
	document.getElementById("cnNumber").focus();

}

function checkLengthCheque(obj){
	if (!isNull(obj.value)) {
		if (obj.value.length < 6) {
			alert("Invalid Cheaque Number");
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return;
		}
	}
}

/**
 * validatePincode
 * 
 * @param pinObj
 * @author sdalli
 */
function validatePincode(pinObj) {
	var consgNumber = document.getElementById("cnNumber").value;
	if(isNull(consgNumber)){
		alert("Please enter Consignment number.");
		document.getElementById("pinCode").value = "";
		document.getElementById("cnNumber").value = "";
		setTimeout(function() {
			document.getElementById("cnNumber").focus();
		}, 10);
		return;
	}
	if (pinObj.value != null && pinObj.value != "") {
		var bookingOfficeId = "";
		var pincode = pinObj.value;
	
		var consgSeries = consgNumber.substring(4, 5);
		// For dlv time
		document.getElementById("prioritySelect").disabled = false;
		var prioritySelect = document.getElementById("prioritySelect");
		prioritySelect.innerHTML = "";
		defOption = document.createElement("option");
		defOption.value = "";
		defOption.appendChild(document.createTextNode("--Select--"));
		prioritySelect.appendChild(defOption);
		if (consgSeries == "p" || consgSeries == "P") {
			document.getElementById("prioritySelect").disabled = false;
		} else {
			document.getElementById("prioritySelect").disabled = true;
		}
		if (pincode.length < 6) {
			alert("Invalid pincode");
			document.getElementById("pinCode").value = "";
			setTimeout(function() {
				document.getElementById("pinCode").focus();
			}, 10);
			return;
		}

		bookingOfficeId = document.getElementById("bookingOfficeId").value;
		showProcessing();
		url = './cashBooking.do?submitName=validatePincode&pincode=' + pincode
				+ "&bookingOfficeId=" + bookingOfficeId + "&consgSeries="
				+ consgSeries;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincode(req);
			}
		});
	}

}

/**
 * printCallBackPincode
 * 
 * @param data
 * @author sdalli
 */
function printCallBackPincode(data) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isValidPincode == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("pinCode").value = "";
				document.getElementById("pinCode").focus();
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
			} else if (cnValidation.isValidPriorityPincode == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("pinCode").value = "";
				document.getElementById("pinCode").focus();
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
			} else {
				isValidPincode=true;
				document.getElementById("destCity").value = cnValidation.cityName;
				document.getElementById("cityId").value = cnValidation.cityId;
				document.getElementById("pincodeId").value = cnValidation.pincodeId;
				getPriorityDtls();
			}
		}
	}
	jQuery.unblockUI();
}

/**
 * validateConsignment
 * 
 * @param consgNumberObj
 * @author sdalli
 */
function validateConsignment(consgNumberObj) {
	if (consgNumberObj.value != null && consgNumberObj.value != "") {
		var bookingType = document.getElementById("bookingType").value;
		var consgNumber = consgNumberObj.value;
		if(isNull(oldConsingment))
		if (consgNumber.length < 12 || consgNumber.length > 12) {
			alert("Consingment length should be 12 characters");
			document.getElementById("cnNumber").value = "";
			setTimeout(function() {
				document.getElementById("cnNumber").focus();
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
		url = './cashBooking.do?submitName=validateConsignment&consgNumber='
				+ consgNumber + "&bookingType=" + bookingType;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignment(req,consgNumberObj.value);
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
function printCallBackConsignment(data,consgNumber) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			// to be remove and sh
			if (cnValidation.isValidCN == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("cnNumber").value = "";
				setTimeout(function() {
					document.getElementById("cnNumber").focus();
				}, 10);
			} else if (cnValidation.isConsgExists == "Y") {
				alert(cnValidation.errorMsg);
				document.getElementById("cnNumber").value = "";
				setTimeout(function() {
					document.getElementById("cnNumber").focus();
				}, 10);
			}
			else {
				oldConsingment = consgNumber;
				usedConsignments.push(consgNumber);
			}
		}
	}
	disabledDiscount();
	//setPaymentMode();
	jQuery.unblockUI();
}

function setPaymentMode() {
	var cnNumver = $("#cnNumber").val();
	var productSeriese = cnNumver.charAt(4);
	if(productSeriese=="T" || productSeriese=="t")
		disablePaymentMode();
	else
		enablePaymentMode();	
}

function disablePaymentMode() {
	$("#payMode").attr("disabled","disabled");
}

function enablePaymentMode() {
	$("#payMode").attr("disabled",false);
}


/**
 * getApprovers
 * 
 * @author sdalli
 */
function getApprovers() {
	var bookingType = "CS";
	var url = "cashBooking.do?submitName=getApprovers&bookingType="
			+ bookingType;
	ajaxCallWithoutForm(url, getApproversResponse);
}

/**
 * getApproversResponse
 * 
 * @param data
 * @author sdalli
 */
function getApproversResponse(data) {
	var content = document.getElementById('approver');
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.employeeId;
		option.appendChild(document.createTextNode(this.firstName + " "
				+ this.lastName));
		content.appendChild(option);
	});

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
	if (docType == consgTypeParcel) {
		url = "./cashBookingParcel.do?submitName=createCashBookingParcel&docType="
				+ docType;
		window.location = url;
	} else if (docType == consgTypeDox) {
		url = './cashBooking.do?submitName=createCashBookingDox';
		window.location = url;
	}

}

// For rate calculations
/**
 * printCallBackCNRate
 * 
 * @param data
 * @author sdalli
 */
function printCallBackCNRate(data) {
	var cnRateDetails = eval('(' + data + ')');
	if (cnRateDetails != null && cnRateDetails != "") {
		if (cnRateDetails.finalPrice != null && cnRateDetails.finalPrice > 0) {
			document.getElementById("cnPrice").value = cnRateDetails.finalPrice;
			document.getElementById("finalCNPrice").value = cnRateDetails.finalPrice;
		}
		if (cnRateDetails.fuelChg != null && cnRateDetails.fuelChg > 0)
			document.getElementById("fuelChg").value = convertToFraction(
					cnRateDetails.fuelChg, 2);
		if (cnRateDetails.riskSurChg != null && cnRateDetails.riskSurChg > 0)
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
		if (cnRateDetails.serviceTax != null && cnRateDetails.serviceTax > 0)
			document.getElementById("serviceTax").value = convertToFraction(
					cnRateDetails.serviceTax, 2);
		if (cnRateDetails.eduCessChg != null && cnRateDetails.eduCessChg > 0)
			document.getElementById("eduCessChg").value = convertToFraction(
					cnRateDetails.eduCessChg, 2);
		if (cnRateDetails.higherEduCessChg != null
				&& cnRateDetails.higherEduCessChg > 0)
			document.getElementById("higherEduCessChg").value = convertToFraction(
					cnRateDetails.higherEduCessChg, 2);
		if (cnRateDetails.freightChg != null && cnRateDetails.freightChg > 0)
			document.getElementById("freightChg").value = convertToFraction(
					cnRateDetails.freightChg, 2);
		jQuery.unblockUI();
	}
}

/**
 * calcDiscountRate
 * 
 * @author sdalli
 */
function calcDiscountRate() {
	var finalCNPrice = document.getElementById('cnPrice').value;
	var discountVal = document.getElementById('discount').value;
	var discAmt = null;
	var finalDis = null;
	if (finalCNPrice != null && discountVal != null) {
		document.getElementById('approver').disabled = false;
		document.getElementById('approver').focus();
		//discAmt = finalCNPrice * (discountVal / 100);
		/*finalDis = finalCNPrice - discAmt;
		document.getElementById('finalCNPrice').value = convertToFraction(
				finalDis, 2);
		document.getElementById('discount').value = parseFloat(discountVal)
				.toFixed(2);*/
	}
}

/**
 * specialChg
 * 
 * @author sdalli
 */
function specialChg() {
	var splChg = document.getElementById('splChg').value;
	if (isNull(splChg))
		splChg = 0.00;
	var refinalVal = null;
	if (finalRate != null) {
		refinalVal = parseInt(finalRate, 10) + parseInt(splChg, 10);
		document.getElementById('finalCNPrice').value = convertToFraction(
				refinalVal, 2);
		document.getElementById('splChg').value = convertToFraction(splChg, 2);
	}
}

/**
 * convertToFraction
 * 
 * @param value
 * @param digits
 * @returns
 * @author sdalli
 */
function convertToFraction(value, digits) {
	var val = parseFloat(value).toFixed(digits);
	return val;
}

/**
 * fixFormatForCurrency
 * 
 * @param obj
 * @author sdalli
 */
function fixFormatForCurrency(obj) {
	if (obj.value != "" && obj.value != null) {
		obj.value = parseFloat(obj.value).toFixed(2);
	} else {
		obj.value = "";
		obj.focus();
		return;
	}
}

/**
 * validateDiscount
 * 
 * @param discountObj
 * @author sdalli
 */
function validateDiscount(discountObj) {
	if (!isNull(discountObj.value)) {
		document.getElementById('approver').disabled = true;
		document.getElementById('approver').value = 0;
		var discount = discountObj.value;
		showProcessing();
		url = './cashBooking.do?submitName=validateDiscount&discount='
				+ discount;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackDiscount(req);
			}
		});
	}

}

/**
 * printCallBackDiscount
 * 
 * @param data
 * @author sdalli
 */
function printCallBackDiscount(data) {
	var consgTypeName = document.getElementById('consgTypeName').value;
	var consgType="";
	if (consgTypeName != null) {
		consgType = consgTypeName.split("#")[1];
	}
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isDiscountExceeded == "Y") {
				if(consgType=='DOX'){
					calcCNrate();
				}else{
					calcCNrateParcel();
				}
				alert(cnValidation.errorMsg);
				document.getElementById("discount").value = "";
				document.getElementById("discount").focus();
			} else {
				calcDiscountRate();
				setTimeout(function() {
					document.getElementById("approver").focus();
				}, 10);
			}
		}
	}
	jQuery.unblockUI();
}

/**
 * disabledDiscount
 * 
 * @author sdalli
 */
function disabledDiscount() {
	var consgNumber = document.getElementById("cnNumber").value;
	if (!isNull(consgNumber)) {
		var consgSeries = consgNumber.substring(4, 5);
		if (consgSeries == "P")
			document.getElementById("discount").disabled = true;
		else
			document.getElementById("discount").disabled = false;
	}
}

/**
 * validatePrivilegeCard
 * 
 * @author sdalli
 */
function validatePrivilegeCard() {
	var privilegeCardNo = document.getElementById("privilegeCardNo").value;
	var privilegeCardAmt = document.getElementById("finalCNPrice").value;
	if (privilegeCardNo == "" || privilegeCardNo == "") {
		alert("Please enter privilege card number.");
		/*
		 * setTimeout(function() {
		 * document.getElementById("privilegeCardNo").focus(); }, 10);
		 */
		return;
	} else if (privilegeCardAmt == "" || privilegeCardAmt == "") {
		alert("Please calculate rate for the consignment.");
		// document.getElementById("payMode").focus();
		return;
	}
	if (privilegeCardNo != null && privilegeCardNo != ""
			&& privilegeCardAmt != null && privilegeCardAmt != "") {
		showProcessing();
		url = './cashBooking.do?submitName=validatePrivilegeCard&privilegeCardAmt='
				+ privilegeCardAmt + "&privilegeCardNo=" + privilegeCardNo;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPrivilegeCard(req);
			}
		});
	}

}

/**
 * printCallBackPrivilegeCard
 * 
 * @param data
 * @author sdalli
 */
function printCallBackPrivilegeCard(data) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isValidPrivilegeCard == "N") {
				alert(cnValidation.errorMsg);
				document.getElementById("privilegeCardNo").value = "";
				// document.getElementById("privilegeCardAmt").value = "";
				document.getElementById("privilegeCardNo").focus();
			} else {
				document.getElementById("privilegeCardId").value = cnValidation.privilegeCardId;
				document.getElementById("privilegeCardAmt").value = cnValidation.privilegeCardAvalBal;
				// calcDiscountRate();
			}
		}
	}
	jQuery.unblockUI();
}

/**
 * clearFields
 * 
 * @author sdalli
 */
function clearFields() {
	document.getElementById("cnPrice").value = "";
	document.getElementById("destCity").value = "";
	document.getElementById("discount").value = "";
	document.getElementById("discount").value = "";
}

/**
 * getPriorityDtls
 * 
 * @author sdalli
 */
function getPriorityDtls() {
	var consgNo = document.getElementById("cnNumber").value;
	var consgSeries = consgNo.substring(4, 5).toUpperCase();
	if(consgSeries == "P") {
		var pincode = document.getElementById("pinCode").value;
		var url = './baBookingParcel.do?submitName=setPriorityServiceValues&pincode='
				+ pincode + '&consgSeries=' + consgSeries;
		ajaxCallWithoutForm(url, getPriorityDtlsResponse);
	}
}

/**
 * getPriorityDtlsResponse
 * 
 * @param data
 * @author sdalli
 */
function getPriorityDtlsResponse(data) {
	var prioritySelect = document.getElementById("prioritySelect");
	if (!isNull(prioritySelect)) {
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.pincodeDeliveryTimeMapId;
			option.appendChild(document.createTextNode(this.deliveryTime));
			prioritySelect.appendChild(option);
		});
	} else {
		alert("Priorary services are not available.");
	}
}

/**
 * capturedWeight
 * 
 * @param data
 * @author sdalli
 */
function capturedWeight(data) {
	var capturedWeight = "";
	if (!isNull(data)) {
		capturedWeight = eval('(' + data + ')');
		if (capturedWeight == -1) {
			wmWeight = capturedWeight;
		} else if (capturedWeight == -2) {
			wmWeight = "Exception occurred";
		} else if (!isNull(capturedWeight) && capturedWeight != -1) {
			wmWeight = parseFloat(capturedWeight).toFixed(3);
			
			if(wmWeight<0){
				alert("Negative Weight Reached");
				weightInkgs=0;
				weightInGms= '000';
				
			}else{
				
				weightInkgs = wmWeight.split(".")[0];
				weightInGms = wmWeight.split(".")[1];
				
			}
			
			
			disableEnableWeight();
		}
	}
}

/**
 * disableEnableWeight
 * 
 * @author sdalli
 */
function disableEnableWeight() {
	if (wmWeight != "-1") {
		document.getElementById("weight").readOnly = true;
		document.getElementById("weightGm").readOnly = true;
		document.getElementById("weight").value = weightInkgs;
		document.getElementById("weightGm").value = weightInGms;
		document.getElementById("weightCapturedMode").value = "A";
		document.getElementById("finalWeight").value = wmWeight;

	} else {
		document.getElementById("weight").readOnly = false;
		document.getElementById("weightGm").readOnly = false;
		document.getElementById("weight").value = "";
		document.getElementById("weightGm").value = "";
		document.getElementById("weightCapturedMode").value = "M";
		document.getElementById("finalWeight").value = "";
	}
}

function callEnterKeyForCash(e, paymentMode) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13 || key == undefined) {
		if (!isNull(paymentMode)) {
			var payMode = paymentMode.split("#")[1];
			if (payMode == "CHQ") {
				document.getElementById("bank").focus();
			} else if (payMode == "PVC") {
				document.getElementById("privilegeCardNo").focus();
			} else {
				document.getElementById("inputSplChg").focus();
			}
		}
		return false;
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
function onlyNumberAndEnterKeyNavPincode(e, Obj, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		if (!isNull(Obj.value)) {

			var consg = document.getElementById("cnNumber").value;
			var consgSeries = consg.substring(4, 5);
			if (consgSeries.toUpperCase() == "P") {
				document.getElementById("prioritySelect").focus();
			} else {
				document.getElementById(focusId).focus();
			}

			return true;
		} else {
			alert("Please enter Valid Pincode.");
			document.getElementById("pinCode").value = "";
			setTimeout(function() {
				document.getElementById("pinCode").focus();
			}, 10);
			return false;
		}
	} else {
		return true;
	}
}

function enableDisabledPriorityService() {
	var consgNumber = document.getElementById("cnNumber").value;
	var consgSeries = consgNumber.substring(4, 5);
	if (consgSeries.toUpperCase() == "P") {
		document.getElementById("prioritySelect").disabled = false;
	} else {
		document.getElementById("prioritySelect").disabled = true;
	}
}

function getPaymentModes() {
	var consgNumber = document.getElementById("cnNumber").value;
	var prodCode = consgNumber.substring(4, 5);
	if(!isNull(consgNumber)) {
		var url = "cashBooking.do?submitName=getPaymentDetails&prodCode="
			+ prodCode;
		ajaxCallWithoutForm(url, setPaymentModes);
	}
}

/**
 * getApproversResponse
 * 
 * @param data
 * @author sdalli
 */
function setPaymentModes(data) {
	var payMode = document.getElementById('payMode');
	payMode.innerHTML = "";
	$.each(data, function(index, value) {
		var option;
		option = document.createElement("option");
		option.value = this.paymentId+"#"+this.paymentCode;
		option.appendChild(document.createTextNode(this.paymentType));
		payMode.appendChild(option);
	});
	showPane();
}


