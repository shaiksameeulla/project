/** The PIECES_NEW */
var PIECES_NEW = 0;
/** The PIECES_OLD */
var PIECES_OLD = 0;
/** The ROW_COUNT */
var ROW_COUNT = "";
/** The DEC_VAL */
var DEC_VAL = "";

$(document).ready(function() {
	var oTable = $('#booking').dataTable({
		"sScrollY" : "310",
		"sScrollX" : "180%",
		"sScrollXInner" : "130%",
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
 * getBABookingsDtls
 *
 * @author sdalli
 */
function getBABookingsDtls() {
	if (checkMadatoryParam()) {
		showProcessing();
		var url = "/udaan-web/baBookingParcel.do?submitName=getBABookingsDtls";
		document.forms["baBookingParcelForm"].action = url;
		document.forms["baBookingParcelForm"].submit();
	}
}

/**
 * isValiedBACode
 *
 * @param baObj
 * @author sdalli
 */
function isValiedBACode(baObj) {
	if (baObj.value != null && baObj.value != "") {
		showProcessing();
		var baCode = document.getElementById("baCode").value;
		url = './baBookingDox.do?submitName=isValiedBACode&baCode=' + baCode;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackBACode(req);
			}
		});
	}

}

/**
 * printCallBackBACode
 *
 * @param data
 * @author sdalli
 */
function printCallBackBACode(data) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			document.getElementById("baId").value = cnValidation;
		} else {
			alert("Please Enter Valid BA code");
			document.getElementById("baCode").value = "";
			document.getElementById("baId").value = "";
			document.getElementById("baCode").focus();
		}
	}
	jQuery.unblockUI();
}

/*Checking Date Field is Mandatory*/

/**
 * checkMadatoryParam
 *
 * @returns {Boolean}
 * @author sdalli
 */
function checkMadatoryParam() {
	var isValid = true;
	var baCode = document.getElementById('baId').value;
	var chequeDate = document.getElementById('bookingDate').value;
	if (chequeDate == null || chequeDate == "") {
		alert("Please enter the booking Date.");
		setTimeout(function() {
			document.getElementById("bookingDate").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (baCode == null || baCode == "") {
		alert("Please Enter the BA Code.");
		setTimeout(function() {
			document.getElementById("baCode").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	return isValid;
}

/**
 * print BA Bookings Dtls
 *
 * @author sdalli
 */
function printBABookingsDtls() {
	if (checkMadatoryParam()) {
		var url = "/udaan-web/baBookingParcel.do?submitName=printBABookingDtls";
		document.forms["baBookingParcelForm"].action = url;
		document.forms["baBookingParcelForm"].submit();
	}
}

function cancelDetails() {
	var flag = confirm("Do you want to cancel the details ?");
	if (flag) {
		url = "baBookingParcel.do?submitName=viewBookingForBA";
		document.baBookingParcelForm.action = url;
		document.baBookingParcelForm.submit();
	}
}

function checkBackDate(fromDate) {
	if (fromDate.value == null || fromDate.value == "")
		return true;
	else {
		if (isFutureDate(fromDate.value)) {
			alert("Future date is not allowed.");
			document.getElementById('bookingDate').value = "";
			document.getElementById('bookingDate').focus();
		} else {
			return true;
		}

	}
}
