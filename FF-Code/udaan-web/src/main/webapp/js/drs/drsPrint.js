function printDrs() {
	if (!isNewDrs()) {
		var drsDom = getDomElementById('drsNumber');
		if (isNull(drsDom.value)) {
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		if (isNull(printDrsUrl)) {
			alert("Please initialise Print DRS Url in your javascript");
			return false;
		}
		if (promptConfirmation('Do you want to Print the DRS Details?')) {
			var localPrint = printDrsUrl + drsDom.value;
			// var w =window.open(localPrint,'DRS
			// Print','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			// showProcessing();
			ajaxCallWithoutForm(localPrint, callBackCommonPrintDrs);
		}
	} else {
		alert("Please find the details ");
		return false;
	}
}

function printCreditCardDrs() {
	if (!isNewDrs()) {
		var drsDom = getDomElementById('drsNumber');
		if (isNull(drsDom.value)) {
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		if (isNull(printDrsUrl)) {
			alert("Please initialise Print DRS Url in your javascript");
			return false;
		}
		if (promptConfirmation('Do you want to Print the DRS Details?')) {
			var localPrint = null;
			localPrint = printDrsUrl + drsDom.value;
			// var w = window.open(localPrint,'DRS
			// Print','height=700,width=900,left=600,top=1200,resizable=yes,scrollbars=auto,location=0');
			// showProcessing();
			ajaxCallWithoutForm(localPrint, callBackCommonPrintDrs);
		}
	} else {
		alert("Please find the details ");
		return false;
	}
}

function printCodLcDrs() {
	if (!isNewDrs()) {
		var drsDom = getDomElementById('drsNumber');
		if (isNull(drsDom.value)) {
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		if (isNull(printDrsUrl)) {
			alert("Please initialise Print DRS Url in your javascript");
			return false;
		}
		if (promptConfirmation('Do you want to Print the DRS Details?')) {
			var localPrint = null;
			localPrint = printDrsUrl + drsDom.value;
			// var w = window.open(localPrint, 'DRS
			// Print','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			// showProcessing();
			ajaxCallWithoutForm(localPrint, callBackCommonPrintDrs);
		}
	} else {
		alert("Please find the details ");
		return false;
	}

}

function printDrsNormPpx() {
	if (!isNewDrs()) {
		var drsDom = getDomElementById('drsNumber');
		if (isNull(drsDom.value)) {
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		if (isNull(printDrsUrl)) {
			alert("Please initialise Print DRS Url in your javascript");
			return false;
		}
		if (promptConfirmation('Do you want to Print the DRS Details?')) {
			var localPrint = null;
			localPrint = printDrsUrl + drsDom.value;
			// var w = window.open(localPrint, 'DRS
			// Print','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			// showProcessing();
			ajaxCallWithoutForm(localPrint, callBackCommonPrintDrs);
		}
	} else {
		alert("Please find the details ");
		return false;
	}

}

function printCodLcPpxDrs() {

	if (!isNewDrs()) {
		var drsDom = getDomElementById('drsNumber');
		if (isNull(drsDom.value)) {
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		if (isNull(printDrsUrl)) {
			alert("Please initialise Print DRS Url in your javascript");
			return false;
		}
		if (promptConfirmation('Do you want to Print the DRS Details?')) {
			var localPrint = null;
			localPrint = printDrsUrl + drsDom.value;
			// var w = window.open(localPrint, 'DRS
			// Print','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			// showProcessing();
			ajaxCallWithoutForm(localPrint, callBackCommonPrintDrs);
		}
	} else {
		alert("Please find the details ");
		return false;
	}
}

function printRtoCodDrs() {

	if (!isNewDrs()) {
		var drsDom = getDomElementById('drsNumber');
		if (isNull(drsDom.value)) {
			alert("DRS-Number should not be empty");
			setFocusOnDom(drsDom);
			return false;
		}
		if (isNull(printDrsUrl)) {
			alert("Please initialise Print DRS Url in your javascript");
			return false;
		}
		if (promptConfirmation('Do you want to Print the DRS Details?')) {
			var localPrint = null;
			localPrint = printDrsUrl + drsDom.value;
			// var w = window.open(localPrint, 'DRS
			// Print','height=700,width=900,left=60,top=120,resizable=yes,scrollbars=auto,location=0');
			// showProcessing();
			ajaxCallWithoutForm(localPrint, callBackCommonPrintDrs);
		}
	} else {
		alert("Please find the details ");
		return false;
	}
}

// Call back function for all print AJAX function
function callBackCommonPrintDrs(ajaxResp) {
	if (!isNull(ajaxResp)) {
		if (ajaxResp != "SUCCESS") {
			var responsetext = jsonJqueryParser(ajaxResp);
			var error = responsetext[ERROR_FLAG];
			if (responsetext != null) {
				if (error != null) {
					alert(error);
				}
			}
		}
	}
	// hideProcessing();
}
