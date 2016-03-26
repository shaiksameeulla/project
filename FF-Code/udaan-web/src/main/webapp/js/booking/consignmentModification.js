/** The MIN_DEC_VAL */
var MIN_DEC_VAL = 0;
/** The DEC_VAL */
var DEC_VAL = 0;
/** The IS_POLICY_MANDATORY */
var IS_POLICY_MANDATORY = "";

var validPincode = true;

/**
 * loadDefaultObjects
 * 
 * @author sdalli
 */
function loadDefaultObjects() {
	loadDoubleVal();
	var consigNo = $('#cnNumber').val();
	enableDisableParcelField();
	if (!isNull(consigNo)) {
		getDomElementById("cnNumber").disabled = true;
		getDomElementById("searchBtn").disabled = true;
		// getDomElementById("searchBtn").removeClass=btnintform;
		jQuery("#" + "Search").removeClass("btnintform");
	}
	// validatePincodeForUpdate(document.getElementById("pinCode"));
	/** Add Consignments in Common Array */
	addConsignmentInUsedCN();
}

function addConsignmentInUsedCN() {
	var consigNo = $('#cnNumber').val();
	if (!isNull(consigNo)) {
		usedConsignments.push(consigNo);
		var cnDetails = document.getElementById("oldChildDetails").value;
		if (isNull(cnDetails) || isNaN(cnDetails)){
			cnDetails = document.getElementById("details").value;
		}
		if (!isNull(cnDetails)) {
			var keyValList = cnDetails.split("#");
			for ( var i = 0; i < keyValList.length; i++) {
				var keyValPair = keyValList[i].split(",");
				var consgNumber = $.trim(keyValPair[0]);
				if (!isNull(consgNumber)){
					usedConsignments.push(consgNumber);
				}
			}
		}
	}
}

function enableDisableParcelField() {
	var docTypeIdName = document.getElementById('consgType').value;
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	if (!isNull(consgType)) {
		document.getElementById('consgTypeCode').value = consgType;
		if (consgType == 'DOX') {
			getDomElementById("weightVW").value = "";
			getDomElementById("weightGmVW").value = "";
			getDomElementById("volWeight").value = "";
			getDomElementById("contentCode").value = "";
			getDomElementById("contentId").value = "";
			getDomElementById("contentName").value = "";
			getDomElementById("cnContentOther").value = "";
			getDomElementById("declaredValue").value = "";
			getDomElementById("paperWork").value = "";
			getDomElementById("paper").value = "";
			getDomElementById("insuaranceNo").value = "";
			getDomElementById("policyNo").value = "";
			getDomElementById("length").value = "";
			getDomElementById("breath").value = "";
			getDomElementById("height").value = "";
			getDomElementById("pieces").value = "";
			getDomElementById("chrgweight").value = "";
			getDomElementById("chrgweightGm").value = "";
			getDomElementById("volImage").style.visibility = 'hidden';
			getDomElementById("weightVW").disabled = true;
			getDomElementById("weightGmVW").disabled = true;
			getDomElementById("volWeight").disabled = true;
			getDomElementById("pieces").disabled = true;
			getDomElementById("contentCode").disabled = true;
			getDomElementById("contentId").disabled = true;
			getDomElementById("contentName").disabled = true;
			getDomElementById("cnContentOther").disabled = true;
			getDomElementById("declaredValue").disabled = true;
			getDomElementById("paperWork").disabled = true;
			getDomElementById("paper").disabled = true;
			getDomElementById("insuaranceNo").disabled = true;
			getDomElementById("policyNo").disabled = true;
			getDomElementById("details").value = "";

		} else {

			getDomElementById("weightVW").disabled = false;
			getDomElementById("volImage").style.visibility = "visible";
			getDomElementById("weightGmVW").disabled = false;
			getDomElementById("volWeight").disabled = false;
			getDomElementById("pieces").disabled = false;
			if (isNull(getDomElementById("pieces").value)) {
				getDomElementById("pieces").value = 1;
			}
			getDomElementById("contentCode").disabled = false;
			getDomElementById("contentId").disabled = false;
			getDomElementById("contentName").disabled = false;
			getDomElementById("cnContentOther").disabled = false;
			getDomElementById("declaredValue").disabled = false;
			getDomElementById("paperWork").disabled = false;
			getDomElementById("paper").disabled = false;
			getDomElementById("insuaranceNo").disabled = false;
			getDomElementById("policyNo").disabled = false;
		}
	}

}
function loadDoubleVal() {
	var domVal = document.getElementById("volWeight").value;
	if (!isNull(domVal)) {
		domVal = parseFloat(domVal).toFixed(4);
		document.getElementById("volWeight").value = domVal;
	}
	var domVal = document.getElementById("finalWeight").value;
	if (!isNull(domVal)) {
		domVal = parseFloat(domVal).toFixed(4);
		document.getElementById("finalWeight").value = domVal;
	}

	var declaredValue = document.getElementById('declaredValue');

	if (!isNull(declaredValue)) {
		var decVal = declaredValue.value;
		if (!isNull(decVal)) {
			decVal = parseFloat(decVal).toFixed(2);
			document.getElementById("declaredValue").value = decVal;

		}
	}

	setDoubleVal(freightChg);
	setDoubleVal(fuelChg);
	setDoubleVal(riskChg);
	setDoubleVal(tpPayChg);
	setDoubleVal(airportHadlChg);
	setDoubleVal(inputSplChg);
	setDoubleVal(serviceTax);
	setDoubleVal(splChg);
	setDoubleVal(eduCessChg);
	setDoubleVal(higherEduCessChg);
	setDoubleVal(finalCNPrice);

}

function setDoubleVal(dom) {
	var domVal = dom.value;
	if (!isNull(domVal)) {
		domVal = parseFloat(domVal).toFixed(2);
		dom.value = domVal;
	}
}

/**
 * getConsignmentDtls
 * 
 * @author sdalli
 */
function getConsignmentDtls() {
	var consg = document.getElementById("cnNumber").value;
	
	if (!isNull(consg)) {
		var consgSeries = consg.substring(4, 5);
		if(consgSeries=="E" || consgSeries=="e"){
			alert("Emotional Bond booked consignment cannot be viewed.");
			document.getElementById("cnNumber").value="";
			document.getElementById("cnNumber").focus();
			return false;
		}
		showProcessing();
		var url = "/udaan-web/consignmentModifcation.do?submitName=getBooking";
		document.forms["consignmentModificationForm"].action = url;
		document.forms["consignmentModificationForm"].submit();

	} else {
		alert("Please enter the consingment number for modifcation.");
		document.getElementById("cnNumber").focus();
	}
}
/**
 * weightFormat
 * 
 * @author sdalli
 */
function weightFormat() {
	var actualWeight = document.getElementById('actualWeight').value;
	var volWeight = document.getElementById('volWeight').value;
	var finalWeight = document.getElementById('finalWeight').value;

	if (!isNull(actualWeight)) {
		var keyValuekg = actualWeight.split(".")[0];
		var keyValueGm = actualWeight.split(".")[1];
		document.getElementById('weight').value = keyValuekg;
		document.getElementById('weightGm').value = keyValueGm;
	}
	if (!isNull(volWeight)) {
		var keyValuekg = volWeight.split(".")[0];
		var keyValueGm = volWeight.split(".")[1];
		document.getElementById('weightVW').value = keyValuekg;
		document.getElementById('weightGmVW').value = keyValueGm;
	}
	if (!isNull(finalWeight)) {
		var keyValuekg = finalWeight.split(".")[0];
		var keyValueGm = finalWeight.split(".")[1];
		document.getElementById('chrgweight').value = keyValuekg;
		document.getElementById('chrgweightGm').value = keyValueGm;
	}

}
/**
 * updateBooking
 * 
 * @author sdalli
 */
function updateBooking() {
	var isConsgClosed = document.getElementById("isConsgClosed").value;
	if (isConsgClosed == "Y") {
		alert("Consingment is manifested hence can't be modify.");
	} else {
		if (validateFields()) {
			inputEnable();
			var url = "/udaan-web/consignmentModifcation.do?submitName=updateBooking";
			document.forms["consignmentModificationForm"].action = url;
			document.forms["consignmentModificationForm"].submit();
		}

	}
}

/*
 * ******Start******View And Edit print function*************
 */
function printViewAndEdit() {
	var consgNumber = $("#cnNumber").val();
	if (!isNull(consgNumber)) {
		url = './consignmentModifcation.do?submitName=printViewAndEditBooking&consgNumber='
				+ consgNumber;
		window
				.open(
						url,
						'newWindow',
						'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=680,height=480,left= 412,top = 184,scrollbars=yes');

		/*
		 * url = "consignmentModifcation.do?submitName=viewConsignment";
		 * document.forms["consignmentModificationForm"].action = url;
		 * document.forms["consignmentModificationForm"].submit();
		 */
	}
}

/*
 * ******End******View And Edit print function*************
 */

function validateFields() {
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
	var prioritySelect = document.getElementById('prioritySelect').value;
	var consgSeries = CNnumber.substring(4, 5);
	var bookingType = document.getElementById('bookingType').value;
	var content = document.getElementById("contentName");
	var cnContentOther = document.getElementById("cnContentOther");
	var declaredValue = document.getElementById('declaredValue');
	var noOfPcs = document.getElementById('pieces');
	var insuredBy = document.getElementById('insuaranceNo');
	var docTypeIdName = document.getElementById('consgType').value;
	var docType = "";
	if (docTypeIdName != null) {
		docType = docTypeIdName.split("#")[1];
	}
	var errorMsgs = "";
	var focusObj = "";

	if (isNull($.trim(CNnumber))) {
		errorMsgs = "Please enter the CN Number.\n";
		focusObj = "cnNumber";

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

	if (consgSeries == "P" || consgSeries == "p") {
		if (isNull(prioritySelect)) {
			errorMsgs = errorMsgs + "Please select the Priortiy Service.\n";
			if (isNull(focusObj)) {
				focusObj = "prioritySelect";
			}

		}
	}
	if (isEmptyWeight(finalWeight) || isNaN(finalWeight)) {
		document.getElementById("weightGm").value = "";
		document.getElementById("weight").value = "";
		errorMsgs = errorMsgs + "Please enter the Weight.\n";
		if (isNull(focusObj)) {
			focusObj = "weight";
		}
		// return isValid;
	}
	if (!isNull(docType) && docType == 'PPX') {
		if (!isNull(content)) {
			var cnContet = content.value;
			var contentCode = "";
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
					alert("Please Enter Other consignment content.");
					setTimeout(function() {
						document.getElementById("cnContentOther").focus();
					}, 10);
					isValid = false;
					return isValid;
				}
			}
		}

		if (!isNull(declaredValue)) {
			if (isNull($.trim(declaredValue.value))) {
				errorMsgs = errorMsgs + "Please enter the Declared Value.\n";
				if (isNull(focusObj)) {
					focusObj = "declaredValue";
				}

			}
		}
		if (!isNull(insuredBy) && insuredBy.value == '2') {
			var policyNo = document.getElementById('policyNo').value;
			if (isNull(policyNo)) {
				errorMsgs = errorMsgs + "Please enter policy number.\n";
				if (isNull(focusObj)) {
					focusObj = "insuaranceNo";
				}
			}
		}
		if (!isNull(noOfPcs)) {
			noOfPcs = parseInt(noOfPcs.value);
			if (isNull(noOfPcs)) {
				errorMsgs = errorMsgs
						+ "Please enter the valid No Of Pieces.\n";
				if (isNull(focusObj)) {
					focusObj = "pieces";
				}

			}

			if (noOfPcs > 1) {
				var childCNDetails = document.getElementById("details").value;
				if (isNull(childCNDetails)) {
					errorMsgs = errorMsgs
							+ "Please capture child consignment details.\n";
					if (isNull(focusObj)) {
						focusObj = "pieces";
					}
				}
			}
		}
	}

	if (bookingType == "CS" || bookingType == "FC" || consgSeries == "P"
			|| consgSeries == "p" || consgSeries == "T" || consgSeries == "t") {

		if (isNull($.trim(cnrName))) {
			errorMsgs = errorMsgs + "Please enter the Consignor Name.\n";
			if (isNull(focusObj)) {
				focusObj = "cnrName";
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

		if (isNull($.trim(cneName))) {
			errorMsgs = errorMsgs + "Please enter the Consignee Name.\n";
			if (isNull(focusObj)) {
				focusObj = "cneName";
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

	}

	if (bookingType == "BK") {
		if (isNull($.trim(cneName))) {
			errorMsgs = errorMsgs + "Please enter the Consignee Name.\n";
			if (isNull(focusObj)) {
				focusObj = "cneName";
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

	}

	if (bookingType == "CS" || bookingType == "FC") {

		var discountVal = document.getElementById('discount').value;
		if (document.getElementById("discount").disabled == false) {
			if (!isNull($.trim(discountVal))) {
				if (document.getElementById("approver").selectedIndex == 0) {
					errorMsgs = errorMsgs
							+ "Please select the Approver's name.\n";
					if (isNull(focusObj)) {
						focusObj = "approver";
					}

				}
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
				document.getElementById('bank').value = "";
				errorMsgs = errorMsgs + "Please enter the Bank Name.\n";
				if (isNull(focusObj)) {
					focusObj = "bank";
				}

			}
			if (isNull($.trim(bankBranchName))) {
				document.getElementById('bankBranchName').value = "";
				errorMsgs = errorMsgs
						+ "Please enter the name of Bank's Branch.\n";
				if (isNull(focusObj)) {
					focusObj = "bankBranchName";
				}

			}
			if (chequeDate == null || chequeDate == "") {
				errorMsgs = errorMsgs + "Please enter the cheque Date.\n";
				if (isNull(focusObj)) {
					focusObj = "chequeDate";
				}

			}

			if (isNull($.trim(cno))) {
				document.getElementById('cno').value = "";
				errorMsgs = errorMsgs + "Please enter the cheque Number.\n";
				if (isNull(focusObj)) {
					focusObj = "cno";
				}

			}
		}

		else if (payMode1 == "PVC") {

			var pcd = document.getElementById('privilegeCardNo').value;
			var pcdAmt = document.getElementById('privilegeCardAmt').value;

			if (isNull($.trim(pcd))) {
				errorMsgs = errorMsgs
						+ "Please enter the Privilege Card Number.\n";
				if (isNull(focusObj)) {
					focusObj = "privilegeCardNo";
				}

			}
			if (isNull($.trim(pcdAmt))) {
				errorMsgs = errorMsgs + "Please enter the Privilege Amount.\n";
				if (isNull(focusObj)) {
					focusObj = "privilegeCardAmt";
				}

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
 * weightFormatForParcelVolGm
 * 
 * @author sdalli
 */
function weightFormatForParcelVolGm() {
	var gmValue = document.getElementById("weightGmVW").value;
	if (gmValue.length == 0) {
		document.getElementById("weightGmVW").value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("weightGmVW").value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("weightGmVW").value += "0";
		gmValue += "0";
	}

	/* Adding additional decimal value to Kg up to 1 decimal */
	var kgValue = document.getElementById("weightVW").value;
	if (kgValue.length == 0) {
		document.getElementById("weightVW").value += "0";
		kgValue += "0";
	}

	document.getElementById("volWeight").value = (kgValue + "." + gmValue);
}

/**
 * weightFormatForParcelChrgGm
 * 
 * @author sdalli
 */
function weightFormatForParcelChrgGm() {
	var gmValue = document.getElementById("chrgweightGm").value;
	if (gmValue.length == 0) {
		document.getElementById("chrgweightGm").value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("chrgweightGm").value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("chrgweightGm").value += "0";
		gmValue += "0";
	}

	/* Adding additional decimal value to Kg up to 1 decimal */
	var kgValue = document.getElementById("chrgweight").value;
	if (kgValue.length == 0) {
		document.getElementById("chrgweight").value += "0";
		kgValue += "0";
	}

	document.getElementById("finalWeight").value = (kgValue + "." + gmValue);
}

/**
 * cancelBookingDetails
 * 
 * @author sdalli
 */
function cancelBookingDetails() {
	var flag = confirm("Do you want to cancel the details ?");
	if (flag) {
		url = "consignmentModifcation.do?submitName=viewConsignment";
		document.forms["consignmentModificationForm"].action = url;
		document.forms["consignmentModificationForm"].submit();
	}
}

/**
 * setContentDtls
 * 
 * @author sdalli
 */
function setContentDtls() {
	document.getElementById("contentCode").value = "";
	document.getElementById("contentId").value = "";
	var contentValue = document.getElementById("contentName").value;
	var contentCode = "";
	var contentId = "";
	if (!isNull(contentValue)) {
		contentCode = contentValue.split("#")[1];
		contentId = contentValue.split("#")[0];
	}
	if (!isNull(contentCode)) {
		document.getElementById("cnContentOther").value = "";
		document.getElementById("cnContentOther").readOnly = true;
		document.getElementById("contentId").value = contentId;
		document.getElementById("contentCode").value = contentCode;
		if (contentCode == "999") {
			document.getElementById("cnContentOther").readOnly = false;
			setTimeout(function() {
				document.getElementById("cnContentOther").focus();
			}, 10);
		}
	}

}

/* Enter key navigation */
function callEnterKeyInContentCash(e, contentCode) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		if (contentCode == 'O') {
			document.getElementById("cnContentOther").focus();
		} else {
			document.getElementById("declaredValue").focus();
		}
		return false;
	}
}
function setChargeableWt() {
	var docTypeIdName = document.getElementById('consgType').value;
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	if (!isNull(consgType)) {
		var weight = document.getElementById("weight").value;
		var weightGm = document.getElementById("weightGm").value;
		var actualWeight = (weight + "." + weightGm);
		actualWeight = parseFloat(actualWeight).toFixed(3);
		if (consgType == 'DOX') {
			document.getElementById("chrgweight").value = weight;
			document.getElementById("chrgweightGm").value = weightGm;
			document.getElementById("actualWeight").value = actualWeight;
			document.getElementById("finalWeight").value = actualWeight;

		} else {
			var weightVW = document.getElementById("weightVW").value;
			var weightGmVW = document.getElementById("weightGmVW").value;
			if (!isEmptyWeight(weightVW) || !isEmptyWeight(weightGmVW)) {
				var volWeight = (weightVW + "." + weightGmVW);
				volWeight = parseFloat(volWeight).toFixed(3);
				if (parseFloat(volWeight) > parseFloat(actualWeight)) {
					document.getElementById("chrgweight").value = weightVW;
					document.getElementById("chrgweightGm").value = weightGmVW;
					document.getElementById("actualWeight").value = actualWeight;
					document.getElementById("volWeight").value = volWeight;
					document.getElementById("finalWeight").value = volWeight;
				} else {
					document.getElementById("chrgweight").value = weight;
					document.getElementById("chrgweightGm").value = weightGm;
					document.getElementById("actualWeight").value = actualWeight;
					document.getElementById("finalWeight").value = actualWeight;
				}
			} else {
				document.getElementById("chrgweight").value = weight;
				document.getElementById("chrgweightGm").value = weightGm;
				document.getElementById("actualWeight").value = actualWeight;
				document.getElementById("finalWeight").value = actualWeight;
			}

		}
	}
}

/**
 * isValidDecValue
 * 
 * @param obj
 * @author sdalli
 */
function isValidDecValueCNModi(obj) {
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
	ajaxCallWithoutForm(url, setBookingDtlsResponseCNModi);

}

/**
 * setBookingDtlsResponse
 * 
 * @param data
 * @author sdalli
 */
function setBookingDtlsResponseCNModi(data) {
	if (!isNull(data)) {
		if (data.isValidDeclaredVal == "N") {
			alert('Declared Value Max limit exceeded.');
			document.getElementById("declaredValue").value = "";
			setTimeout(function() {
				document.getElementById("declaredValue").focus();
			}, 10);
		} else {
			getPaperWorksForCNModi();
			getInsuarnceConfigDtlsCNModi();
			calcCNRate4ViewEdit();
		}
	}
}

/**
 * getPaperWorks
 * 
 * @author sdalli
 */
function getPaperWorksForCNModi() {
	var declaredValue = document.getElementById('declaredValue').value;
	var pincode = document.getElementById('pinCode').value;
	var docTypeIdName = document.getElementById('consgType').value;
	var docType = "";
	if (docTypeIdName != null) {
		docType = docTypeIdName.split("#")[1];
	}
	if (!isNull(pincode)) {
		var url = "cashBookingParcel.do?submitName=getPaperWorks&pincode="
				+ pincode + "&declaredValue=" + declaredValue + "&docType="
				+ docType;
		ajaxCallWithoutForm(url, getPaperWorksResponseForCNModi);
	}
}

/**
 * getPaperWorksResponse
 * 
 * @param data
 * @author sdalli
 */
function getPaperWorksResponseForCNModi(data) {
	var content = document.getElementById('paperWork');
	content.innerHTML = "";
	defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("--Select--"));
	content.appendChild(defOption);
	if (!isNull(data)) {
		$.each(data, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.cnPaperWorkId;
			option.appendChild(document.createTextNode(this.cnPaperWorkName));
			content.appendChild(option);
		});
	}
}

/**
 * getInsuarnceConfigDtls
 * 
 * @param obj
 * @author sdalli
 */
function getInsuarnceConfigDtlsCNModi() {
	var bookingType = document.getElementById("bookingType").value;
	var decVal = document.getElementById("declaredValue").value;
	DEC_VAL = decVal;
	url = './cashBookingParcel.do?submitName=getInsuarnceConfigDtls&declaredVal='
			+ decVal + '&bookingType=' + bookingType;
	ajaxCallWithoutForm(url, setInsDtlsResponseCNModi);
}

/**
 * setInsDtlsResponse
 * 
 * @param data
 * @author sdalli
 */
function setInsDtlsResponseCNModi(data) {
	if (!isNull(data)) {
		var insDetails = data;
		if (insDetails.length == 1) {
			for ( var i = 0; i < insDetails.length; i++) {
				if (insDetails[i].trasnStatus != "NOINSURENCEMAPPING") {
					var contentValueArr = document
							.getElementById('insuaranceNo');
					for ( var i = 0; i < contentValueArr.length; i++) {
						var selectObj = contentValueArr[i];
						var selectedVal = selectObj.value;
						if (selectedVal == insDetails.insuredById) {
							selectObj.selected = 'selected';
						}
						if (DEC_VAL >= MAX_DECLARED_VALUE) {
							if (selectedVal == 1) {
								contentValueArr.remove(i);
							}
						} else {
							getInsuredByDtls(0);
						}

					}
				} else {
					alert('There is no proper mapping for declared value hence not allowed.');
					document.getElementById("declaredValue").value = "";
					setTimeout(function() {
						document.getElementById("declaredValue").focus();
					}, 10);
				}
			}
		}
		/*
		 * if (insDetails.trasnStatus != "NOINSURENCEMAPPING") { var
		 * contentValueArr = document.getElementById('insuaranceNo'); for ( var
		 * i = 0; i < contentValueArr.length; i++) { var selectObj =
		 * contentValueArr[i]; var selectedVal = selectObj.value; if
		 * (selectedVal == insDetails.insuredById) { selectObj.selected =
		 * 'selected'; } if (DEC_VAL >= MAX_DECLARED_VALUE) { if (selectedVal ==
		 * 1) { contentValueArr.remove(i); } } else { getInsuredByDtls(0); } } }
		 * else { alert('There is no proper mapping for declared value hence not
		 * allowed.'); document.getElementById("declaredValue").value = "";
		 * setTimeout(function() {
		 * document.getElementById("declaredValue").focus(); }, 10); }
		 */
	}
}

/**
 * validatePincode
 * 
 * @param pinObj
 * @author sdalli
 */
function validatePincodeForUpdate(pinObj) {
	if (pinObj.value != null && pinObj.value != "") {
		var bookingOfficeId = "";
		var pincode = pinObj.value;
		var consgNumber = document.getElementById("cnNumber").value;
		var consgSeries = consgNumber.substring(4, 5);
		var pincodeField = parseFloat(pincode);
		if (pincode.length < 6 || pincodeField == "0") {
			validPincode = false;
			alert("Invalid pincode");
			document.getElementById("pinCode").value = "";
			setTimeout(function() {
				document.getElementById("pinCode").focus();
			}, 10);
			return false;
		}
		bookingOfficeId = document.getElementById("bookingOfficeId").value;
		showProcessing();
		url = './cashBooking.do?submitName=validatePincode&pincode=' + pincode
				+ "&bookingOfficeId=" + bookingOfficeId + "&consgSeries="
				+ consgSeries;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackPincodeForEdit(req);
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
function printCallBackPincodeForEdit(data) {
	var response = data;
	hideProcessing();
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isValidPincode == "N") {
				validPincode = false;
				alert(cnValidation.errorMsg);
				document.getElementById("pinCode").value = "";
				document.getElementById("pinCode").focus();
				return false;
			} else if (cnValidation.isValidPriorityPincode == "N") {
				validPincode = false;
				alert(cnValidation.errorMsg);
				document.getElementById("pinCode").value = "";
				document.getElementById("pinCode").focus();
				return false;
			} else {
				validPincode = true;
				document.getElementById("destCity").value = cnValidation.cityName;
				document.getElementById("cityId").value = cnValidation.cityId;
				document.getElementById("pincodeId").value = cnValidation.pincodeId;
				calcCNRate4ViewEdit();
			}
		}
	}

}

function validateManifest() {
	if (updateStatus == "M") {
		alert("Consignment is already Manifested, Edit is not allowed !");
		disableAll();
		jQuery("#" + "cancel").attr("disabled", false);
		jQuery("#" + "cancel").removeClass("btnintformbigdis");
		jQuery("#" + "cancel").addClass("btnintform");

	}
}

function calcCNRate4ViewEdit() {
	var consgNumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var docTypeIdName = document.getElementById('consgType').value;
	var discountOnCN = document.getElementById('discount').value;
	var inputsplChg = document.getElementById('inputSplChg').value;
	var splChg = document.getElementById('splChg').value;
	var customerId = document.getElementById('customerId').value;
	var priorityService = jQuery('#prioritySelect option:selected').text();
	var declaredValue = document.getElementById('declaredValue').value;
	var insuredBy = $('#insuaranceNo').val();
	
	var consgType = "";
	var rateType = document.getElementById('rateType').value;
	var discount = "";
	var splCharges = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}

	var productCode = consgNumber.substring(4, 5);
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if (!isNull(discountOnCN))
		discount = discountOnCN;
	if (!isNull(inputsplChg)){
		splCharges = inputsplChg;
	}else{
		splCharges=splChg;
	}
	
	var codAmt = document.getElementById('codAmount').value;
	var lcAmt = document.getElementById('lcAmount').value;
	
	if (validPincode) {
		if (!isNull(consgNumber) && !isNull(pincode)
				&& !isEmptyWeight(finalWeight) && !isNaN(finalWeight)) {
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
					+ "&consgNumber="
					+ consgNumber + "&customerId=" + customerId
					+ "&codAmt=" + codAmt + "&lcAmt=" + lcAmt+"&insuredBy="+insuredBy; // Foc parcel bookings could not able to modify hence added param 'insuredBy'
			jQuery.ajax({
				url : url,
				type : "POST",
				success : function(req) {
					printCallBackCNRateDetails4ViewEdit(req);
				}
			});
		}
	}/*
		 * else{ alert("Please enter mandatory fields for new rates."); }
		 */

}

function printCallBackCNRateDetails4ViewEdit(data) {
	document.getElementById("consgRateDtls").value = "";
	hideProcessing();
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			document.consignmentModificationForm.action = "/udaan-web/consignmentModifcation.do?submitName=viewConsignment";
			document.consignmentModificationForm.submit();
		} else {
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

}

function onlyNumberAndEnterKeyNavWeightKG(event) {
	var consgNumber = document.getElementById('cnNumber').value;
	var docTypeIdName = document.getElementById('consgType').value;
	var bookingType = document.getElementById('bookingType').value;
	var consgSeries = consgNumber.substring(4, 5);
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	if (!isNull(consgType) && validateWeightForViewAndEdit(event, "weight")) {
		if (consgType == 'DOX'
				&& (bookingType == "CS" || bookingType == "FC"
						|| consgSeries == "P" || consgSeries == "p"
						|| consgSeries == "T" || consgSeries == "t")) {
			return callEnterKey(event, document.getElementById('cnrName'));
		} else {
			return callEnterKey(event, document.getElementById('weightGm'));

		}
	}

}

function onlyNumberAndEnterKeyNavWeightGM(event) {
	var consgNumber = document.getElementById('cnNumber').value;
	var docTypeIdName = document.getElementById('consgType').value;
	var bookingType = document.getElementById('bookingType').value;
	var consgSeries = consgNumber.substring(4, 5);
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	if (!isNull(consgType) && validateWeightForViewAndEdit(event, "weightGm")) {
		if (consgType == 'DOX') {
			if (bookingType == "CS" || bookingType == "FC"
					|| consgSeries == "P" || consgSeries == "p"
					|| consgSeries == "T" || consgSeries == "t") {
				return callEnterKey(event, document.getElementById('cnrName'));
			} else {
				return callEnterKey(event, document.getElementById('modify'));
			}
		}
		if (consgType == 'PPX') {
			return callEnterKey(event, document.getElementById('contentName'));
		}

	}
}

function onlyNumberAndEnterKeyNavPincode(event, obj) {
	var docTypeIdName = document.getElementById('consgType').value;
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	if (!isNull(consgType) && consgType == 'DOX') {
		return onlyNumberAndEnterKeyNav(event, obj, 'weightGm');
	} else {
		return onlyNumberAndEnterKeyNav(event, obj, 'weight');
	}

}

/**
 * validateDiscount
 * 
 * @param discountObj
 * @author sdalli
 */
function validateDiscountConsg(discountObj) {
	if (!isNull(discountObj.value)) {
		var discount = discountObj.value;
		showProcessing();
		url = './cashBooking.do?submitName=validateDiscount&discount='
				+ discount;
		jQuery.ajax({
			url : url,
			success : function(req) {
				printCallBackDiscountConsg(req);
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
function printCallBackDiscountConsg(data) {
	var response = data;
	if (response != null) {
		var cnValidation = eval('(' + response + ')');
		if (cnValidation != null && cnValidation != "") {
			if (cnValidation.isDiscountExceeded == "Y") {

				alert(cnValidation.errorMsg);
				document.getElementById("discount").value = "";
				document.getElementById("discount").focus();
				calcCNRate4ViewEdit();
			} else {
				calcCNRate4ViewEdit();
				setTimeout(function() {
					document.getElementById("approver").focus();
				}, 10);
			}
		}
	}
	jQuery.unblockUI();
}

function disabledDiscountConsg() {
	var consgNumber = document.getElementById("cnNumber").value;
	var bookingType = document.getElementById("bookingType").value;

	if (!isNull(consgNumber)) {
		if (bookingType == 'CS' || bookingType == 'FC') {
			var consgSeries = consgNumber.substring(4, 5);
			if (consgSeries == "P") {
				document.getElementById("discount").disabled = true;
				document.getElementById("approver").disabled = true;
			} else {
				document.getElementById("discount").disabled = false;
				document.getElementById("approver").disabled = false;
			}
		}
	}
}

function showPaneOnLoad() {
	var paymentMode = document.getElementById('payMode').value;
	var payMode = paymentMode.split("#")[1];
	if (payMode == 'CHQ')// Cheque
	{
		Obj("tblcheque").style.display = "";
		Obj("tblbankbranch").style.display = "";
		Obj("tblprivcard").style.display = "none";

	} else if (payMode == 'PVC')// Credit Card
	{
		Obj("tblcheque").style.display = "none";
		Obj("tblbankbranch").style.display = "none";
		Obj("tblprivcard").style.display = "";

	} else if (payMode == 'CA')// Cash
	{

		Obj("tblcheque").style.display = "none";
		Obj("tblbankbranch").style.display = "none";
		Obj("tblprivcard").style.display = "none";

	}
}

function redirectToChildCNPageCNModi() {
	var pieces = document.getElementById('pieces').value;

	PIECES_NEW = pieces;
	PIECES_OLD = PIECES_NEW;
	OLD_CHILD_CN = new Array();
	if (PIECES_NEW < PIECES_ACTUAL) {
		PIECES_OLD = 0;
	}
	var cnDetails = document.getElementById("oldChildDetails").value;
	var keyValList = cnDetails.split("#");
	var oldCNCount = 0;
	for ( var i = 0; i < keyValList.length; i++) {
		var keyValPair = keyValList[i].split(",");
		var consgNumber = $.trim(keyValPair[0]);
		if (!isNull(consgNumber)) {
			oldCNCount = oldCNCount+1;
		}
	}
	
	if (PIECES_OLD != PIECES_NEW) {
		var cnDetails = document.getElementById("details").value;
		var keyValList = cnDetails.split("#");
		for ( var i = 0; i < keyValList.length; i++) {
			var keyValPair = keyValList[i].split(",");
			var consgNumber = $.trim(keyValPair[0]);
			if (!isNull(consgNumber)) {
				for ( var j = 0; j < usedConsignments.length; j++) {
					if (usedConsignments[j] == consgNumber) {
						if (PIECES_NEW > oldCNCount) {
							continue;
						} else {
							usedConsignments[j] = null;
						}

					}
				}
			}
		}
	}
	var cnDetails = document.getElementById("oldChildDetails").value;
	var keyValList = cnDetails.split("#");
	for ( var i = 0; i < keyValList.length; i++) {
		var keyValPair = keyValList[i].split(",");
		var consgNumber = $.trim(keyValPair[0]);
		OLD_CHILD_CN.push(consgNumber);
		if (!isNull(consgNumber)) {
			for ( var j = 0; j < usedConsignments.length; j++) {
				if (usedConsignments[j] == consgNumber) {
					if (PIECES_NEW > oldCNCount) {
						continue;
					} else {
						usedConsignments[j] = null;
					}
				}
			}
		}
	}

	if (pieces != null && pieces != "" && pieces > 1) {
		var processCode = "BOOK";
		url = "childCNPopup.do?pieces=" + pieces + '&processCode='
				+ processCode;
		window
				.open(
						url,
						'newWindow',
						'toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=200,left = 412,top = 184');
		PIECES_ACTUAL = PIECES_NEW;
	}

}

function validateWeightForViewAndEdit(e, wtField) {
	setChargeableWt();
	var weight = document.getElementById("weight").value;
	var weightGm = document.getElementById("weightGm").value;
	var actualWt = document.getElementById("actualWeight").value;
	var docTypeIdName = document.getElementById('consgType').value;
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which;
	var consgType = "";
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	if (isNaN(actualWt)) {
		actualWt = "0.000";
	}
	actualWt = parseFloat(actualWt).toFixed(3);
	if (isEmptyWeight(actualWt)) {
		if (key == 13) {
			if (!isNull(consgType)) {
				if (consgType == 'DOX') {
					alert("Please Enter Valid Weight.");
					setTimeout(function() {
						document.getElementById("weightGm").focus();
					}, 10);

				} else {
					if (wtField != "weight") {
						alert("Please Enter Valid Weight.");
						setTimeout(function() {
							document.getElementById("weight").focus();
						}, 10);

					} else {
						return true;
					}
				}
			}
			return false;
		} else {
			return true;
		}

	} else {
		return true;
	}
}