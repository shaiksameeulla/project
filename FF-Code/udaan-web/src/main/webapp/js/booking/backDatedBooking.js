/**
 * upload
 *
 * @returns {Boolean}
 * @author sdalli
 */
function upload() {

	if (ValidateMandatoryFields()) {
		showProcessing();
		var fileValue = document.getElementById('fileUpload').value;
		var fileType = document.getElementById('fileType').value;

		if (isNull(fileValue)) {
			alert('Please select a file to upload');
			return false;
		} else {
			var ext = fileValue.split(".");
			if (!(ext[1] == 'xls' || ext[1] == 'xlsx')) {
				alert('Files with only xls and xlsx format are allowed');
				return false;
			}

		}
		document.backdatedBookingForm.action = "/udaan-web/backdatedBooking.do?submitName=fileUploadBackdatedBooking";
		document.backdatedBookingForm.submit();
		//jQuery.unblockUI();
	}
}

/**
 * validateHeaderResponse
 *
 * @param data
 * @author sdalli
 */
function validateHeaderResponse(data) {

}

/**
 * checkDate
 *
 * @param fromDate
 * @returns {Boolean}
 * @author sdalli
 */
function checkDate(fromDate) {
	var toDate = document.getElementById("sysdate").value;
	if (fromDate == null || fromDate == "")
		return true;
	else {
		if (!isFutureDate(fromDate)) {
			var dateDiff = calculateDateDiffExcludeSundays(fromDate, toDate);
			if (dateDiff == 0) {
				alert("Today's date is not allowed.");
				document.getElementById('dlvDateTime').value = "";
				document.getElementById('dlvDateTime').focus();
				} else {
					if (dateDiff > maxBackBookingDateAllowed) {
						alert("Date selected can be only 3 days before current date");
						document.getElementById('dlvDateTime').value = "";
						document.getElementById('dlvDateTime').focus();
				}
			}
		} else {
			alert("Future date is not allowed.");
			document.getElementById('dlvDateTime').value = "";
			document.getElementById('dlvDateTime').focus();
		}

	}
}

function allowOnlyNumbers(e){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	//alert("charCode"+charCode);
	if ((charCode >= 48 && charCode <= 58) || charCode == 9 || charCode == 8 || charCode==0) {
		return true;
	}
	else{
		return false;
	}
	return false;
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
			alert("Please enter time in HH:MM format and time should not be greater than 23:59");
			obj.value = "";
			obj.focus();
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

/**
 * getAllOffices
 *
 * @author sdalli
 */
function getAllOffices() {
	regionId = document.getElementById("destRegionId").value;
	if (!isNull(regionId)) {
		url = './backdatedBooking.do?submitName=getAllOfficesByRegion&regionId='
				+ regionId;
		ajaxCallWithoutForm(url, printAllOffices);
	}

}

/**
 * printAllOffices
 *
 * @param data
 * @author sdalli
 */
function printAllOffices(data) {
	if (!isNull(data)) {
		var content = document.getElementById('branch');
		content.innerHTML = "";
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.officeId;
			option.appendChild(document.createTextNode(this.officeName));
			content.appendChild(option);
		});

	}
}

/**
 * ValidateMandatoryFields
 *
 * @returns {Boolean}
 * @author sdalli
 */
function ValidateMandatoryFields() {

	var isValid = true;
	var dlvDateTime = document.getElementById('dlvDateTime').value;
	var dlvTime = document.getElementById('dlvTime').value;
	var destRegionId = document.getElementById('destRegionId').value;
	var branch = document.getElementById('branch').value;
	var browseText = document.getElementById('fileUpload').value;

	if (isNull(dlvDateTime)) {
		alert("Please enter the Date.");
		setTimeout(function() {
			document.getElementById("dlvDateTime").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	if (dlvTime == null || dlvTime == "") {
		alert("Please enter the Time.");
		setTimeout(function() {
			document.getElementById("dlvTime").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(destRegionId)) {
		alert("Please select the Region");
		setTimeout(function() {
			document.getElementById("destRegionId").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (isNull(branch)) {
		alert("Please select the Branch");
		setTimeout(function() {
			document.getElementById("branch").focus();
		}, 10);
		isValid = false;
		return isValid;
	}

	if (browseText == null || browseText == "") {
		alert("Please select the File.");
		setTimeout(function() {
			document.getElementById("browseText").focus();
		}, 10);
		isValid = false;
		return isValid;
	}
	return isValid;
}

/**
 * cancelBackdatedBooking
 *
 * @author sdalli
 */
function cancelBackdatedBooking() {
	var flag = confirm("Do you want to cancel?");
	if (flag) {
		document.backdatedBookingForm.action = "/udaan-web/backdatedBooking.do?submitName=viewBackdatedBooking";
		document.backdatedBookingForm.submit();
	}
}

/**
 * getErrorList
 *
 * @author sdalli
 */
function getErrorList(){
	var fileName = document.getElementById('fileName').value;
	 window.open('backdatedBooking.do?submitName=getfileUploadErrorList&fileName='+fileName);
}