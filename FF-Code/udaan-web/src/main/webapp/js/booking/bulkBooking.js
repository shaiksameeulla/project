var isValidConsg = false;
var customerCode = "";
var newCustomerCode = "";

/**
 * checkConsgType
 * 
 * 
 * @author sdalli
 */
function checkConsgType() {

	var e = document.getElementById("stdType");
	var stdTypeCode = e.options[e.selectedIndex].value;
	if (stdTypeCode == 'C') {
		document.getElementById("startConsgNo").value = "";
		document.getElementById("endConsgNo").value = "";
		document.getElementById("productCode").value = "";
		document.getElementById("startConsgNo").disabled = true;
		document.getElementById("endConsgNo").disabled = true;
		document.getElementById("productCode").disabled = true;

	} else {
		document.getElementById("startConsgNo").disabled = false;
		document.getElementById("endConsgNo").disabled = false;
		document.getElementById("productCode").disabled = false;
	}
}

function isValidConsFormat(consgNumberObj, value) {
	if (!isNull(consgNumberObj.value)) {
		if (consgNumberObj.value.length < 12
				|| consgNumberObj.value.length > 12) {
			alert("Consingment length should be 12 characters");
			consgNumberObj.value = "";
			setTimeout(function() {
				consgNumberObj.focus();
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
			} else if (value == "end") {
				isValidSeries();
			}
		}
	}
}
/**
 * isValidSeries
 * 
 * @author sdalli
 */
function isValidSeries() {
	var prd = document.getElementById("productCode");
	var prdValue = prd.options[prd.selectedIndex].value;
	if (isNull(prdValue)) {
		alert("Please select the product");
		document.getElementById("startConsgNo").value = "";
		document.getElementById("endConsgNo").value = "";
		setTimeout(function() {
			document.getElementById("productCode").focus();
		}, 10);
	} else {
		var consgSeries = prdValue.split("#")[1];
		var startNo = document.getElementById("startConsgNo").value;
		var endNo = document.getElementById("endConsgNo").value;
		var consgSeriesSel = startNo.charAt(4);
		var consgSeriesSe2 = endNo.charAt(4);
		if (isText(consgSeriesSel)) {
			consgSeriesSel = "N";
		}
		if (isText(consgSeriesSe2)) {
			consgSeriesSe2 = "N";
		}
		if (consgSeriesSel != consgSeries) {
			alert('Invalid start CN number');
			document.getElementById("startConsgNo").value = "";
			document.getElementById("endConsgNo").value = "";
			setTimeout(function() {
				document.getElementById("startConsgNo").focus();
			}, 10);
			return;
		} else if (consgSeriesSe2 != consgSeries) {
			alert('Invalid end CN number');
			document.getElementById("endConsgNo").value = "";
			setTimeout(function() {
				document.getElementById("endConsgNo").focus();
			}, 10);
			return;
		} else {
			validateCNCount(startNo, endNo, consgSeries);
		}
	}
}

function isValidSeriesForSubmit() {
	var isValid = true;
	if (document.getElementById("startConsgNo").disabled == false
			&& document.getElementById("endConsgNo").disabled == false) {
		var prd = document.getElementById("productCode");
		var prdValue = prd.options[prd.selectedIndex].value;
		if (isNull(prdValue)) {
			alert("Please select the product");
			document.getElementById("startConsgNo").value = "";
			document.getElementById("endConsgNo").value = "";
			setTimeout(function() {
				document.getElementById("productCode").focus();
			}, 10);
			isValid = false;
			return isValid;
		} else {
			var consgSeries = prdValue.split("#")[1];
			var startNo = document.getElementById("startConsgNo").value;
			var endNo = document.getElementById("endConsgNo").value;
			var consgSeriesSel = startNo.charAt(4);
			var consgSeriesSe2 = endNo.charAt(4);
			if (isText(consgSeriesSel)) {
				consgSeriesSel = "N";
			}
			if (isText(consgSeriesSe2)) {
				consgSeriesSe2 = "N";
			}
			if (consgSeriesSel != consgSeries) {
				alert('Invalid start CN number');
				document.getElementById("startConsgNo").value = "";
				document.getElementById("endConsgNo").value = "";
				setTimeout(function() {
					document.getElementById("startConsgNo").focus();
				}, 10);
				isValid = false;
				return isValid;
			} else if (consgSeriesSe2 != consgSeries) {
				alert('Invalid end CN number');
				document.getElementById("endConsgNo").value = "";
				setTimeout(function() {
					document.getElementById("endConsgNo").focus();
				}, 10);
				isValid = false;
				return isValid;
			}
		}

	}
	return isValid;
}
/**
 * validateCNCount
 * 
 * @param startNo
 * @param endNo
 * @param consgSeries
 * @author sdalli
 */
function validateCNCount(startNo, endNo, consgSeries) {
	var count = 0;
	var startCnNo = 0;
	var endCnNo = 0;
	// var startNo = document.getElementById("startConsgNo").value;
	// var endNo = document.getElementById("endConsgNo").value;
	if (consgSeries != "N") {
		startCnNo = startNo.split(consgSeries)[1];
		endCnNo = endNo.split(consgSeries)[1];
	} else {
		startCnNo = startNo.substring(4, 12);
		endCnNo = endNo.substring(4, 12);
	}
	count = getCNCount(startCnNo, endCnNo);
	// validate Bookings
	if (!isNull(count)) {
		document.getElementById("cnCount").value = count;
		validateConsingents(startNo, count);
	}
}

/**
 * validateConsingents
 * 
 * @param startCnNo
 * @param count
 * @author sdalli
 */
function validateConsingents(startCnNo, count) {
	var customerId = document.getElementById("custCode").value;
	var bookingOfficeId = document.getElementById("bookingOfficeId").value;
	var bookingOfficeCode = document.getElementById("bookingOfficeCode").value;
	if (!isNull(customerId)) {
		showProcessing();
		url = './bulkBooking.do?submitName=validateConsignments&startCnNumber='
				+ startCnNo + "&quantity=" + count + "&officeId="
				+ bookingOfficeId + "&bookingOfficeCode=" + bookingOfficeCode
				+ "&customerId=" + customerId;

		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackConsignment(req);
			}
		});
	} else {
		alert("Please select the valid customer.");
		document.getElementById("startConsgNo").value = "";
		document.getElementById("endConsgNo").value = "";
		setTimeout(function() {
			document.getElementById("custCode").focus();
		}, 10);
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
	if (!isNull(response)) {
		alert(response);
		document.getElementById("startConsgNo").value = "";
		document.getElementById("endConsgNo").value = "";
		document.getElementById("cnCount").value = 0;
		setTimeout(function() {
			document.getElementById("startConsgNo").focus();
		}, 10);
	} else {
		isValidConsg = true;
	}
	jQuery.unblockUI();
}

/**
 * upload
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function upload() {
	if (isValidSeriesForSubmit()) {
		if (ValidateMandatoryFields()) {
			var custValue = getSelectedDropDownText('custCode');
			if (!isNull(custValue)) {
				var cust = custValue.split("-");
				if (cust.length > 0) {
					cust = cust[0];
				}
				document.getElementById('customerCode').value = cust;

			}
			var fileValue = document.getElementById('fileUpload').value;

			if (isNull(fileValue)) {
				alert('Please choose a file to upload');
				return false;
			} else {
				var ext = fileValue.split(".");
				if (!(ext[1] == 'xls' || ext[1] == 'xlsx')) {
					alert('Files with only xls and xlsx format are allowed');
					return false;
				}

			}
			showProcessing();
			document.creditCustomerBookingParcelForm.action = "/udaan-web/bulkBooking.do?submitName=fileUploadBulkBooking";
			document.creditCustomerBookingParcelForm.submit();
		}
	}
}
/**
 * validateCustCode
 * 
 * @param obj
 * @author sdalli
 */
function validateCustCode(obj) {
	var custCode = document.getElementById('custCode').value;
	if (custCode.length != 10) {
		alert("Customer Code Should be 10 digits.");
		document.getElementById("custCode").value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return;
	}
	showProcessing();
	url = './bulkBooking.do?submitName=validateCustCode&custCode=' + custCode;
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
		document.getElementById("custCode").value = "";
		setTimeout(function() {
			document.getElementById("custCode").focus();
		}, 10);
	} else if (custDetails.isCustValid == "Y") {
		document.getElementById('customerId').value = custDetails.custID;
	}
	jQuery.unblockUI();
}

/**
 * getCNCount
 * 
 * @param startNo
 * @param endNo
 * @returns {Number}
 * @author sdalli
 */
function getCNCount(startNo, endNo) {
	var cnCount = 0;
	if (!isNull(startNo) && !isNull(endNo)) {
		var startNo1 = parseIntNumber(startNo);
		var endNo1 = parseIntNumber(endNo);
		if (startNo1 >= endNo1) {
			alert("Start CN number should not be higher than End number!.");
			document.getElementById("startConsgNo").value = "";
			setTimeout(function() {
				document.getElementById("startConsgNo").focus();
			}, 10);
			return;
		} else if (endNo1 <= startNo1) {
			alert("End CN number should not be lower than Start number!.");
			document.getElementById("endConsgNo").value = "";
			setTimeout(function() {
				document.getElementById("endConsgNo").focus();
			}, 10);
			return;
		} else {
			cnCount = endNo1 - startNo1;
			cnCount = cnCount + parseInt(1);
		}
	}
	return cnCount;
}

/**
 * getErrorList
 * 
 * @author sdalli
 */
function getErrorList(fileName) {
	window.open('bulkBooking.do?submitName=getfileUploadErrorList&fileName='
			+ fileName);
}

/**
 * ValidateMandatoryFields
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function ValidateMandatoryFields() {
	var isValid = true;
	var docType = document.getElementById('docType').value;
	var customerId = document.getElementById('custCode').value;
	var consgType = document.getElementById('stdType').value;
	var productCode = document.getElementById('productCode').value;
	var startConsgNo = document.getElementById('startConsgNo').value;
	var endConsgNo = document.getElementById('endConsgNo').value;
	var browseText = document.getElementById('fileUpload').value;

	if (isNull(docType)) {
		alert("Please select the document type.");
		setTimeout(function() {
			document.getElementById("docType").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(customerId)) {
		alert("Please select valid customer.");
		setTimeout(function() {
			document.getElementById("custCode").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (isNull(consgType)) {
		alert("Please select consignment printing type.");
		setTimeout(function() {
			document.getElementById("stdType").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (consgType != 'C') {
		if (isNull(startConsgNo)) {
			alert("Please enter the start consingment number.");
			setTimeout(function() {
				document.getElementById("startConsgNo").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
		if (isNull(endConsgNo)) {
			alert("Please enter the end consingment number.");
			setTimeout(function() {
				document.getElementById("endConsgNo").focus();
			}, 10);
			isValid = false;
			return isValid;
		}

		if (isNull(productCode)) {
			alert("Please select the product.");
			setTimeout(function() {
				document.getElementById("productCode").focus();
			}, 10);
			isValid = false;
			return isValid;
		}
	}

	if (browseText == null || browseText == "") {
		alert("Please enter the File.");
		setTimeout(function() {
			document.getElementById("browseText").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	return isValid;
}

/**
 * chckFormat
 * 
 * @param obj
 * @author sdalli
 */
function chckFormat(obj) {
	if (obj != null) {
		var format = new RegExp("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$");
		if (!document.getElementById("dlvTime").value.match(format)) {
			alert("Please enter time in HH:MM format");
			document.getElementById('dlvTime').value = "";
			document.getElementById('dlvTime').focus();
		} else {
			var date = document.getElementById('dlvDateTime').value;
			var dateTime = obj.value;
			document.getElementById('deliveryDateTime').value = date + " "
					+ dateTime;
			document.getElementById('bookingDate').value = date + " "
					+ dateTime;
		}
	}
}

function cancelBulkBooking() {
	if (promptConfirmation("cancel")) {
		document.getElementById('docType').value = "";
		document.getElementById('custCode').value = "";
		document.getElementById('stdType').value = "";
		document.getElementById('productCode').value = "";
		document.getElementById('startConsgNo').value = "";
		document.getElementById('endConsgNo').value = "";
		document.getElementById('fileUpload').value = "";
		document.getElementById('cnCount').value = "";
		document.getElementById('customerCode').value = "";

		/*
		 * document.creditCustomerBookingParcelForm.action =
		 * "/udaan-web/bulkBooking.do?submitName=bulkBookingView";
		 * document.creditCustomerBookingParcelForm.submit();
		 */
	}
}

function getCustCode(custObj) {

	var cust = jQuery("#custCode").text();
	if (!isNull(cust)) {
		var custValue = cust.split("-")[0];
		document.getElementById('customerCode').value = custValue;
	}
}

function validateConsgAndCust(obj) {
	newCustomerCode = obj.value;
	if (document.getElementById("startConsgNo").disabled == false
			&& document.getElementById("endConsgNo").disabled == false
			&& ((!isNull(document.getElementById("startConsgNo").value)) || (!isNull(document
					.getElementById("endConsgNo").value)))) {
		if (isValidConsg) {
			var isConfirmChanges = confirm("Consignment number already entered.Do u still want to change customer?");
			if (isConfirmChanges) {
				jQuery("#custCode").val(newCustomerCode);
				document.getElementById("startConsgNo").value = "";
				document.getElementById("endConsgNo").value = "";
				isValidConsg = false;
			} else {
				jQuery("#custCode").val(customerCode);
			}
		}
	}
	customerCode = newCustomerCode;
}
function enterKeyForProductCode(event) {
	var e = document.getElementById("stdType");
	var stdTypeCode = e.options[e.selectedIndex].value;
	if (stdTypeCode == 'C') {
		return callEnterKey(event, document.getElementById('fileUpload'));

	} else {
		return callEnterKey(event, document.getElementById('productCode'));
	}

}