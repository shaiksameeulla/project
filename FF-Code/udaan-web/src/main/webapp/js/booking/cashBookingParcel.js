/** The MIN_DEC_VAL */
var MIN_DEC_VAL = 0;
/** The DEC_VAL */
var DEC_VAL = 0;
/** The IS_POLICY_MANDATORY */
var IS_POLICY_MANDATORY = "N";
var MAX_DECLARED_VALUE = 100000;
var PIECES_ACTUAL = 0;

/**
 * saveOrUpdateCashBookingParcel
 * 
 * @author sdalli
 */
function saveOrUpdateCashBookingParcel() {
	if (validateBookingParcelFields()) {
		var priorityService = jQuery('#prioritySelect option:selected').text();
		if (priorityService != "---Select---") {
			document.getElementById('priorityServiced').value = priorityService;
		}
		enablePaymentMode();
		showProcessing();
		url = './cashBookingParcel.do?submitName=saveOrUpdateCashBookingParcel';
		ajaxCall(url, "cashBookingParcelForm", printCallBackSaveCashBookParcel);
	}
}

/**
 * printCallBackSaveCashBookParcel
 * 
 * @param data
 * @author sdalli
 */
function printCallBackSaveCashBookParcel(data) {
	
	if (data != null && data != "") {
		var bookRes = new Array();
		bookRes = data.split("#");
		if (bookRes[0] == "SUCCESS") {
			alert(bookRes[1]);
			if (confirm("Do You want to print?")) {
				printCashBookingParcel();
			}
		} else if (bookRes[0] == "FAILURE") {
			alert(bookRes[1]);
		}
		redirectPage();
		jQuery.unblockUI();
	}
}

/**
 * isValidDecValue
 * 
 * @param obj
 * @author sdalli
 */
function isValidDecValue(obj) {
	var bookingType = document.getElementById("bookingType").value;
	var decVal = obj.value;
	var decValue = parseFloat(decVal);
	/*if (decValue == "0" || isNull(decVal)) {
		alert("Please enter a non-zero declared value.");
		obj.value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}*/
	url = './baBookingParcel.do?submitName=validateDeclaredvalue&declaredVal='
			+ decVal + '&bookingType=' + bookingType;
	ajaxCallWithoutForm(url, setBookingDtlsResponse);
}

/**
 * setBookingDtlsResponse
 * 
 * @param data
 * @author sdalli
 */
function setBookingDtlsResponse(data) {
	if (!isNull(data)) {
		if (data.isValidDeclaredVal == "N") {
			alert('Declared Value Max limit exceeded.');
			if(!isNull(document.getElementById("fuelChg")))
			document.getElementById("fuelChg").value="";
			if(!isNull(document.getElementById("riskChg")))
			document.getElementById("riskChg").value="";
			if(!isNull(document.getElementById("tpPayChg")))
			document.getElementById("tpPayChg").value="";
			if(!isNull(document.getElementById("airportHadlChg")))
			document.getElementById("airportHadlChg").value="";
			if(!isNull(document.getElementById("splChg")))
			document.getElementById("splChg").value="";
			if(!isNull(document.getElementById("serviceTax")))
			document.getElementById("serviceTax").value="";
			if(!isNull(document.getElementById("eduCessChg")))
			document.getElementById("eduCessChg").value="";
			if(!isNull(document.getElementById("higherEduCessChg")))
			document.getElementById("higherEduCessChg").value="";
			if(!isNull(document.getElementById("freightChg")))
			document.getElementById("freightChg").value="";
			if(!isNull(document.getElementById("finalCNPrice")))
			document.getElementById("finalCNPrice").value="";
			if(!isNull(document.getElementById("cnPrice")))
			document.getElementById("cnPrice").value="";
			document.getElementById("declaredValue").value = "";
			setTimeout(function() {
				document.getElementById("declaredValue").focus();
			}, 10);
		} else {
			calcCNrateParcel();
			getPaperWorks();
			getInsuarnceConfigDtls();
		}
	}
}


/**
 * getPaperWorks
 * 
 * @author sdalli
 */
function getPaperWorks() {
	var declaredValue = document.getElementById('declaredValue').value;
	var pincode = document.getElementById('pinCode').value;
	var docType = "";
	var docTypeIdName = document.getElementById('consgTypeName').value;
	if (docTypeIdName != null)
		docType = docTypeIdName.split("#")[1];
	if (pincode != null && pincode != "") {
		var url = "cashBookingParcel.do?submitName=getPaperWorks&pincode="
				+ pincode + "&declaredValue=" + declaredValue + "&docType="
				+ docType;
		ajaxCallWithoutForm(url, getPaperWorksResponse);
	}
}

/**
 * getPaperWorksResponse
 * 
 * @param data
 * @author sdalli
 */
function getPaperWorksResponse(data) {
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
 * validateBookingParcelFields
 * 
 * @returns {Boolean}
 * @author sdalli
 */
function validateBookingParcelFields() {
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

	var paymentMode = document.getElementById('payMode').value;
	var declaredValue = document.getElementById('declaredValue').value;
	var prioritySelect = document.getElementById('prioritySelect').value;
	var consgSeries = CNnumber.substring(4, 5);
	var content = document.getElementById("contentName");
	var cnContentOther = document.getElementById("cnContentOther");
	var payMode = paymentMode.split("#")[1];
	var noOfPcs = document.getElementById('pieces');
	var insuredBy = document.getElementById('insuaranceNo').value;
	
	var errorMsgs = "";
	var focusObj = "";

	if (isNull($.trim(CNnumber))) {
		document.getElementById('cnNumber').value="";
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
	if (isEmptyWeight(actualWeight) || isNaN(actualWeight)) {
		document.getElementById("weightGm").value = "";
		document.getElementById("weight").value = "";

		errorMsgs = errorMsgs + "Please enter the Weight.\n";
		if (isNull(focusObj)) {
			focusObj = "weight";
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
		if (isNull($.trim(cnContentOther.value))
				|| isNull($.trim(cnContentOther.value))) {
			
			document.getElementById('cnContentOther').value="";
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

	if (paperWork.options.length>1) {
		if (isNull(prdValue)) {
		errorMsgs = errorMsgs + "Please Select the Paperworks.\n";
			if (isNull(focusObj)) {
				focusObj = "paperWork";
			}
		}
	}

	if (!isNull(insuredBy) && insuredBy == '2') {
		var polacyNo = document.getElementById('policyNo').value;
		if (isNull($.trim(polacyNo))) {
			document.getElementById('policyNo').value="";
			errorMsgs = errorMsgs + "Please enter policy number.\n";
			if (isNull(focusObj)) {
				focusObj = "insuaranceNo";
			}
		}
	}

	if (isNull($.trim(cnrName))) {
		document.getElementById('cnrName').value="";
		
		errorMsgs = errorMsgs + "Please enter the Consignor Name.\n";
		if (isNull(focusObj)) {
			focusObj = "cnrName";
		}

	}

	if (isNull($.trim(cneName))) {
		document.getElementById('cneName').value="";
		errorMsgs = errorMsgs + "Please enter the Consignee Name.\n";
		if (isNull(focusObj)) {
			focusObj = "cneName";
		}

	}

	var discountVal = document.getElementById('discount').value;
	if (document.getElementById("discount").disabled == false) {
		if (!isNull($.trim(discountVal))) {
			if (document.getElementById("approver").selectedIndex == 0) {
				errorMsgs = errorMsgs + "Please select the Approver's name.\n";
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
			document.getElementById('bank').value="";
			errorMsgs = errorMsgs + "Please enter the Bank Name.\n";
			if (isNull(focusObj)) {
				focusObj = "bank";
			}

		}
		if (isNull($.trim(bankBranchName))) {
			document.getElementById('bankBranchName').value="";
			errorMsgs = errorMsgs + "Please enter the name of Bank's Branch.\n";
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
			document.getElementById('cno').value="";
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
			errorMsgs = errorMsgs + "Please enter the Privilege Card Number.\n";
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
	if (isNull($.trim(cnrMobile)) && isNull($.trim(cnrPhone))) {
		errorMsgs = errorMsgs
				+ "Please enter the Consignors Mobile or Phone Number.\n";
		if (isNull(focusObj)) {
			focusObj = "cneMobile";
		}
	}

	if (isNull($.trim(cneMobile)) && isNull($.trim(cnePhone))) {
		errorMsgs = errorMsgs
				+ "Please enter the Consignees Mobile or Phone Number.\n";
		if (isNull(focusObj)) {
			focusObj = "cnrMobile";
		}
	}

	if (!isNull(noOfPcs) && isNull(noOfPcs.value)) {
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
 * cancelBookingParcelDetails
 * 
 * @author sdalli
 */
function cancelBookingParcelDetails() {
	if (promptConfirmation("cancel")) {
		document.cashBookingParcelForm.action = "/udaan-web/cashBooking.do?submitName=createCashBookingParcel";
		document.cashBookingParcelForm.submit();
	}
}

/**
 * redirectToChildCNPage
 * 
 * @author sdalli
 */
function redirectToChildCNPage() {
	var pieces = document.getElementById('pieces').value;
	
	PIECES_NEW = pieces;
	PIECES_OLD = PIECES_NEW;
	OLD_CHILD_CN = new Array();
	if (PIECES_NEW < PIECES_ACTUAL) {
		PIECES_OLD = 0;
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
		window.open(url,'newWindow','toolbar=0,scrollbars=1,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=200,left = 412,top = 184');
		PIECES_ACTUAL = PIECES_NEW;
	}

}

/**
 * redirectToVolumetricWeight
 * 
 * @author sdalli
 */
function redirectToVolumetricWeight() {
	var volume = document.getElementById('volWeight').value;
	url = "./volumetricWeight.do?volume" + volume;
	window
			.open(
					url,
					'newWindow',
					'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=0,width=500,height=200,left = 412,top = 184');

}

/**
 * contentVal
 * 
 * @param code
 * @author sdalli
 */
function contentVal(code) {
	var isValidContent = "";
	var contentValueArr = document.getElementById("contentName");
	document.getElementById("contentId").value = "";
	for ( var i = 0; i < contentValueArr.length; i++) {
		var selectObj = contentValueArr[i];
		var selectedVal = selectObj.value;
		var selectedCode = selectedVal.split("#")[1];
		var conetntId = selectedVal.split("#")[0];
		if (selectedCode == code) {
			document.getElementById("contentId").value = conetntId;
			selectObj.selected = 'selected';
			isValidContent = "Y";
			break;
		} else {
			isValidContent = "N";
		}
	}
	if (isValidContent == "N") {
		document.getElementById("contentCode").value = "";
		document.getElementById("contentName").value = "";
		alert("Invalid content code.");
		setTimeout(function() {
			document.getElementById("contentCode").focus();
		}, 10);
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

/**
 * getFinalWeight
 * 
 * @author sdalli
 */
function getFinalWeight() {

	var volWeight = document.getElementById("volWeight").value;
	var actWeight = document.getElementById("actualWeight").value;
	volWeight = parseFloat(volWeight);

	actWeight = parseFloat(actWeight);

	if (!isNull(actWeight)) {
		document.getElementById("finalWeight").value = actWeight;
	}
	if (!isNull(volWeight) && !isNull(actWeight)) {
		if (volWeight > actWeight)
			document.getElementById("finalWeight").value = volWeight;
	}
	// Child Cns validation
	var noOfPcs = document.getElementById("pieces").value;
	var finalWeight = document.getElementById("finalWeight").value;
	if (parseInt(noOfPcs) > 1) {
		var chilsCNDtls = document.getElementById("details").value;
		validateParentAndChildCnsWeight(actWeight, chilsCNDtls, 0);
	}
}

/* Adding additional decimal value to Kg up to 1 decimal */
/**
 * weightFormatForParcelGm
 * 
 * @author sdalli
 */
function weightFormatForParcelGm() {
	var gmValue = document.getElementById("weightGm").value;
	var kgValue = document.getElementById("weight").value;
	
	var finalWeight = parseFloat(kgValue + "." + gmValue);
	

	
	if (gmValue.length == 0) {
		document.getElementById("weightGm").value = "000";
		gmValue += "000";
	} else if (gmValue.length == 1) {
		document.getElementById("weightGm").value += "00";
		gmValue += "00";
	} else if (gmValue.length == 2) {
		document.getElementById("weightGm").value += "0";
		gmValue += "0";
	}

	/* Adding additional decimal value to Kg up to 1 decimal */
	if (kgValue.length == 0) {
		document.getElementById("weight").value += "0";
		kgValue += "0";
	} 
	//var finalWeight = parseFloat(kgValue + "." + gmValue).toFixed(3);
	document.getElementById("actualWeight").value = finalWeight;
}

/**
 * cancelBookingParcelDetails
 * 
 * @author sdalli
 */
function cancelBookingParcelDetails() {
	if (promptConfirmation("cancel")) {
		var docType = "PPX";
		document.cashBookingParcelForm.action = "/udaan-web/cashBookingParcel.do?submitName=createCashBookingParcel&docType="
				+ docType;
		document.cashBookingParcelForm.submit();
	}
}

/**
 * getInsuarnceConfigDtls
 * 
 * @param obj
 * @author sdalli
 */
function getInsuarnceConfigDtls() {
	var bookingType = document.getElementById("bookingType").value;
	var decVal = document.getElementById("declaredValue").value;
	DEC_VAL = decVal;
	url = './cashBookingParcel.do?submitName=getInsuarnceConfigDtls&declaredVal='
			+ decVal + '&bookingType=' + bookingType;
	ajaxCallWithoutForm(url, setInsDtlsResponse);
}

/**
 * setInsDtlsResponse
 * 
 * @param data
 * @author sdalli
 */
function setInsDtlsResponse(data) {
	if (!isNull(data)) {
		var insDetails = data;
		if(insDetails.length==1){
			for ( var i = 0; i < insDetails.length; i++) {
				if (insDetails[i].trasnStatus != "NOINSURENCEMAPPING") {
					var contentValueArr = document.getElementById('insuaranceNo');
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
		/*if (insDetails.trasnStatus != "NOINSURENCEMAPPING") {
			var contentValueArr = document.getElementById('insuaranceNo');
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
		}*/
	}
}

/**
 * isValidInsuredBy
 * 
 * @param obj
 * @returns {Boolean}
 * @author sdalli
 */
function isValidInsuredBy(obj) {
	var bookingType = document.getElementById("bookingType").value;
	var decVal = document.getElementById("declaredValue").value;
	var insuredBy = document.getElementById("insuaranceNo").value;
	url = './cashBookingParcel.do?submitName=validateInsuarnceConfigDtls&declaredVal='
			+ decVal
			+ '&bookingType='
			+ bookingType
			+ '&insuredBy='
			+ insuredBy;
	ajaxCallWithoutForm(url, validateInsDtlsResponse);
}

function validateInsDtlsResponse(data) {
	if (!isNull(data)) {
		var insDetails = data;
		if (insDetails.trasnStatus != "NOINSURENCEMAPPING") {
			IS_POLICY_MANDATORY = insDetails.isPolicyNoMandatory;
		}
	}
}

/**
 * isPolicy
 * 
 * @param obj
 * @returns {Boolean}
 * @author sdalli
 */
function isPolicy(obj) {
	var insDetails = document.getElementById("insuaranceNo").value;
	var policyNo = document.getElementById("policyNo").value;
	if (!isNull(insDetails)) {
		if (isNull(policyNo) && IS_POLICY_MANDATORY == "Y") {
			alert('Please enter the policy Number');
			setTimeout(function() {
				document.getElementById("policyNo").focus();
			}, 10);
			return false;
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

function calculatePriorityRate() {
	var priorityService = jQuery('#prioritySelect option:selected').text();
	if (priorityService != "---Select---") {
		calcCNrate();
	}
}

function calcCNrateParcel() {
	var consgType = "";
	var rateType = "CH";
	var discount = "";
	var splCharges = "";
	var consgNumber = document.getElementById('cnNumber').value;
	var pincode = document.getElementById('pinCode').value;
	var finalWeight = document.getElementById('finalWeight').value;
	var docTypeIdName = document.getElementById('consgTypeName').value;
	var declaredValue = document.getElementById('declaredValue').value;
	var insuredBy = document.getElementById('insuaranceNo').value;
	var priorityService = jQuery('#prioritySelect option:selected').text();
	if (docTypeIdName != null) {
		consgType = docTypeIdName.split("#")[1];
	}
	var productCode = consgNumber.substring(4, 5);
	var originOfficeId = document.getElementById('bookingOfficeId').value;
	if(!isNull(document.getElementById("discount"))){
	var discountOnCN = document.getElementById('discount').value;
	if (!isNull(discountOnCN))
		discount = discountOnCN;
	}
	if(!isNull(document.getElementById("inputSplChg"))){
	var splChg = document.getElementById('inputSplChg').value;
	if (!isNull(splChg))
		splCharges = splChg;
	}
	if (!isNull(consgNumber) && !isNull(pincode) && !isNull(finalWeight)
			&& !isNull(declaredValue) && !isNaN(finalWeight) && isValidPincode==true) {
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
				+ "&declaredValue="
				+ declaredValue
				+ "&insuredBy="
				+ insuredBy
				+ "&priorityService="
				+ priorityService + "&consgNumber=" + consgNumber;
		jQuery.ajax({
			url : url,
			type : "POST",
			success : function(req) {
				printCallBackCNRateDetailsParcel(req);
			}
		});
	}

}



function printCallBackCNRateDetailsParcel(data) {
	document.getElementById("consgRateDtls").value = "";
	if (data != null && data != "") {
		var cnRateDetails = jsonJqueryParser(data);
		if (!isNull(cnRateDetails.message)) {
			alert(cnRateDetails.message);
			var docType = "PPX";
			document.cashBookingParcelForm.action = "/udaan-web/cashBookingParcel.do?submitName=createCashBookingParcel&docType="
					+ docType;
			document.cashBookingParcelForm.submit();
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
			document.getElementById("finalCNPrice").value = convertToFraction(
					cnRateDetails.finalPrice, 2);
			document.getElementById("cnPrice").value = convertToFraction(
					cnRateDetails.finalPrice, 2);
			finalRate = convertToFraction(cnRateDetails.finalPrice, 2);
		}
	}
	jQuery.unblockUI();
}

function validateConsignmentCashParcel(consgNumberObj) {
	var consgNumber = document.getElementById("cnNumber").value;
	if (!isNull(consgNumber) && !isNull(oldConsingment)
			&& consgNumber != oldConsingment) {
		var confirm = window
				.confirm("Header details have been changed, do you clear the entered consignment data?");
		if (confirm) {
			document.cashBookingParcelForm.action = "/udaan-web/cashBookingParcel.do?submitName=createCashBookingParcel";
			document.cashBookingParcelForm.submit();
		} else {
			document.getElementById("cnNumber").value = oldConsingment;
		}
	} else {
		validateConsignment(consgNumberObj);
	}
}

function enterKeyForInsuredBy(event, obj) {
	if (obj.value == '1') {
		return callEnterKey(event, document.getElementById('cnrName'));
	} else if (obj.value == '2') {
		return callEnterKey(event, document.getElementById('policyNo'));
	}
}

function printCashBookingParcel() {

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

function onlyNumberAndEnterKeyNavDecVal(e, Obj, focusId){
	if(validateDecVal(e,Obj)) {
		return onlyNumberAndEnterKeyNav(e, Obj, focusId);
	}
	
}

function validateDecVal(e,Obj) {
	var decVal = Obj.value;
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which;
	if (isNull($.trim(decVal))) {
		if (key == 13) {
			alert("Please enter the Declared Value.");
					setTimeout(function() {
						Obj.focus();
					}, 10);

				} else {
			return true;
		}

	} else {
		return true;
	}
}

function onlyNumberAndEnterKeyNavWeightCasParcel(e, Obj, focusId){
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13 && validateWeightForParcel(e,focusId)) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
	
}

function weightFormatForParcelGmEnter() {
	var gmValue = document.getElementById("weightGm").value;
	var kgValue = document.getElementById("weight").value;
	
	var finalWeight = parseFloat(kgValue + "." + gmValue);


	/* Adding additional decimal value to Kg up to 1 decimal */
	if (kgValue.length == 0) {
		document.getElementById("weight").value += "0";
		kgValue += "0";
	} 
	//var finalWeight = parseFloat(kgValue + "." + gmValue).toFixed(3);
	document.getElementById("actualWeight").value = finalWeight;
}

/*
function onlyNumberAndEnterKeyNavWeightCasParcel(e, Obj, focusId) {
	if(validateWeightForParcel(e)) {
		var weight;
		weight = onlyNumberAndEnterKeyNav(e, Obj, focusId);
		return weight;
	}

}*/

function validateWeightForParcel(e,focusId) {
	weightFormatForParcelGmEnter();
	var weight = document.getElementById("weight").value;
	var weightGm = document.getElementById("weightGm").value;
	var actualWt = document.getElementById("actualWeight").value;
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which;
	if (isNaN(actualWt)) {
		actualWt = "0.000";
	}
	actualWt = parseFloat(actualWt).toFixed(3);
	document.getElementById("weight").value=weight;
	document.getElementById("weightGm").value=weightGm;
	if (isEmptyWeight(actualWt)) {
		if (key == 13) {
			if(focusId!="weightGm"){
			alert("Please Enter Valid Weight.");
			setTimeout(function() {
				document.getElementById("weight").focus();
			}, 10);
			return false;
			}else{
				return true;
			}
		} else {
			return true;
		}

	} else {
		return true;
	}
}
