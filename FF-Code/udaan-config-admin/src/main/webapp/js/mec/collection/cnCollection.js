var ERROR_FLAG = "ERROR";

$(document).ready(function() {
	var oTable = $('#cnCollectionDetails').dataTable({
		"sScrollY" : "250",
		"sScrollX" : "100%",
		"sScrollXInner" : "200%",
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

function isJsonResponce(ObjeResp) {
	var responseText = null;
	responseText = jsonJqueryParser(ObjeResp);
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (!isNull(error)) {
			alert(error);
			hideProcessing();
			return true;
		}
	}
	return false;
}

// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;

/**
 * To add new rows to CN Collection grid
 */
function addCnCollectionRow() {
	$('#cnCollectionDetails')
			.dataTable()
			.fnAddData(
					[
							'<input type="checkbox" id="chk'
									+ rowCount
									+ '" name="to.checkboxes" onclick="validateCheckbox(this);" /><input type="hidden" name="to.isChecked" id="isChecked'
									+ rowCount + '" />',
							rowCount,
							'<input type="text" class="txtbox width110" id = "txnNo'
									+ rowCount
									+ '" name="to.txnNo" value ="" readonly="true" tabindex="-1"/>\
		 <input type="hidden" name="to.collectionEntryId" id="collectionEntryId'
									+ rowCount
									+ '" value =""/>\
		 <input type="hidden" name="to.collectionID" id="collectionID'
									+ rowCount + '" value =""/>',
							'<input type="text" id="receiptNo'
									+ rowCount
									+ '" name="to.receiptNo" class="txtbox width70" size="11" onkeypress="return checkNumWithEnterKey(event,\'tdsAmt'
									+ rowCount
									+ '\');" maxlength="11" value =""/>',
							'<input type="text" id="cnNo'
									+ rowCount
									+ '" name="to.cnNo" class="txtbox width130" size="11" readonly="true" tabindex="-1" value =""/>\
		 <input type="hidden" name="to.consgIds" id="consgIds'
									+ rowCount + '" value =""/>',
							'<input type="text" name = "to.collectionType" id="collectionType'
									+ rowCount
									+ '" class="txtbox width70" size="11" readonly="true" tabindex="-1" value =""/>',
							'<input type="text" name = "to.amount" id="amount'
									+ rowCount
									+ '" class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" readonly="true" tabindex="-1" value =""/>',
							'<input type="text" name = "to.rcvdAmt" id="rcvdAmt'
									+ rowCount
									+ '" maxlength="10" class="txtbox width70" size="11" readonly="true" tabindex="-1" value =""/>',
							'<input type="text" name = "to.tdsAmt" id="tdsAmt'
									+ rowCount
									+ '" maxlength="10" class="txtbox width70" size="11" onkeypress="return checkDecimalWithEnterKey(event,\'mode'
									+ rowCount
									+ '\');" onchange="calcRcvdAmt(this);" value =""/>',
							'<input type="text" name = "to.totals" id="total'
									+ rowCount
									+ '" maxlength="10" class="txtbox width70" size="11" readonly="true" tabindex="-1" value =""/>',
							'<select id="mode'
									+ rowCount
									+ '" name="to.mode" class="selectBox width145" onchange="checkPaymentModeDtls(this);" onkeypress="return enterKeyForPaymentMode(event,'
									+ rowCount + ');">' + modeOptions
									+ '</select>',
							'<select id="cnfor'
									+ rowCount
									+ '" name="to.cnfor" class="selectBox width145" onchange="checkCollForDtls(this);" onkeypress="return callEnterKey(event, document.getElementById(\'chqNo'
									+ rowCount + '\'));">' + cnForOptions
									+ '</select>',
							'<input type="text" name = "to.chqNo" readonly="true" id="chqNo'
									+ rowCount
									+ '" maxlength="6" class="txtbox width70" size="11" onkeypress="return checkNumWithEnterKey(event,\'chqDate'
									+ rowCount
									+ '\');" onblur="validateChqNumber(this);" value =""/>',
							'<input type="text" name = "to.chqDate" readonly="true" id="chqDate'
									+ rowCount
									+ '" class="txtbox width70" size="11" onkeypress="return callEnterKey(event, document.getElementById(\'bankName'
									+ rowCount
									+ '\'));" value =""/>&nbsp;<a id="calendar'
									+ rowCount
									+ '" href="javascript:show_calendar(\'chqDate'
									+ rowCount
									+ '\', this.value);" title="Select Date"><img src="images/icon_calendar.gif" alt="Search" width="16" height="16" border="0" class="imgsearch"/></a>',
							'<input type="text" id="bankName'
									+ rowCount
									+ '" readonly="true" name="to.bankName" maxlength="25" class="txtbox width70" onkeypress="return callEnterKey(event, document.getElementById(\'bankGL'
									+ rowCount + '\'));" value =""/>',
							'<select id="bankGL'
									+ rowCount
									+ '" name="to.bankGLs" class="selectBox width145" onkeypress="return callEnterKey(event, document.getElementById(\'reasonId'
									+ rowCount + '\'));">' + bankGLOptions
									+ '</select>',
							'<select id="reasonId'
									+ rowCount
									+ '" name="to.reasonIds" class="selectBox width145" onchange="validateReasons(this);" onkeypress="return lastObjEnterKeyNavi(event,'
									+ rowCount
									+ ');" >'
									+ reasonOptions
									+ '</select>\
		 <input type="hidden" name="to.cnDeliveryDt" id="cnDeliveryDt'
									+ rowCount + '" value =""/>' ]);
	// getModeDtls(document.getElementById("mode" + rowCount));
	// getCnforList(document.getElementById("cnfor" + rowCount));
	// getBankIdList(document.getElementById("bankId" + rowCount));
	rowCount++;
	serialNo++;
}

/**
 * To check payment mode details
 * 
 * @param domObj
 */
function checkPaymentModeDtls(domObj) {
	rowId = getRowId(domObj, "mode");
	if (domObj.value == CHQ_MODE) {
		$('#chqNo' + rowId).removeAttr("readonly");
		$('#chqNo' + rowId).removeAttr("tabindex");
		// $('#chqDate' + rowId).removeAttr("readonly");
		$("#bankName" + rowId).removeAttr("readonly");
		$('#bankName' + rowId).removeAttr("tabindex");
		$("#calendar" + rowId).show();
		// Disabled fields
		getDomElementById("cnfor" + rowId).disabled = false;
		getDomElementById("bankGL" + rowId).disabled = false;
	} else {
		jQuery("#chqNo" + rowId).val("");
		jQuery("#bankName" + rowId).val("");
		jQuery('#chqDate' + rowId).val("");
		jQuery("#chqNo" + rowId).attr("readonly", true);
		jQuery("#bankName" + rowId).attr("readonly", true);
		$("#calendar" + rowId).hide();
		getDomElementById("chqNo" + rowId).setAttribute("tabindex", "-1");
		getDomElementById("chqDate" + rowId).setAttribute("tabindex", "-1");
		getDomElementById("bankName" + rowId).setAttribute("tabindex", "-1");
		// Reset values
		getDomElementById("cnfor" + rowId).value = COLL_FOR_FFCL;
		getDomElementById("bankGL" + rowId).value = "";
		getDomElementById("cnfor" + rowId).disabled = true;
		getDomElementById("bankGL" + rowId).disabled = true;
	}
}

function ajaxCallWithoutForm(pageurl, ajaxResponse) {
	progressBar();
	jQuery.ajax({
		url : pageurl,
		type : "GET",
		dataType : "json",
		success : function(data) {
			jQuery.unblockUI();
			ajaxResponse(data);
		},
		async : false
	});
	jQuery.unblockUI();
}

/**
 * On load get default Objects
 */
function loadDefaultObjects() {
	// addCnCollectionRow();
	getTodaysDeliverdConsgDtls();
}

// Ajax call to populate list of Payment Mode.
/*
 * var modegridId; function getModeDtls(domObj){ modegridId =
 * getRowId(domObj,"mode"); var
 * url="./cnCollection.do?submitName=getPaymentModeDtls";
 * ajaxCallWithoutForm(url, ajxRespForModeDtls); } //Ajax call response to
 * populate list of CollectionAgainst function ajxRespForModeDtls(ajaxResp){ if
 * (!isNull(ajaxResp)) { var content =
 * document.getElementById('mode'+modegridId); content.innerHTML = "";
 * $.each(ajaxResp, function(index, value) { var option; option =
 * document.createElement("option"); option.value = this.paymentId;
 * option.appendChild(document.createTextNode(this.paymentType));
 * content.appendChild(option); }); jQuery.unblockUI(); } }
 */

// Ajax call to populate list of Bank List.
// This function is not getting called from anywhere
var bankId;
function getBankIdList(domObj) {
	bankId = getRowId(domObj, "bankId");
	var url = "./cnCollection.do?submitName=getBankDtls";
	ajaxCallWithoutForm(url, ajxRespForBankIdList);
}
// Ajax call response to populate list of CollectionAgainst
function ajxRespForBankIdList(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var content = document.getElementById('bankId' + bankId);
		content.innerHTML = "";
		option = document.createElement("option");
		option.value = "";
		option.appendChild(document.createTextNode("----Select----"));
		content.appendChild(option);
		$.each(ajaxResp, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.bankId;
			option.appendChild(document.createTextNode(this.bankName));
			content.appendChild(option);
		});
		jQuery.unblockUI();
	}
}

// Ajax call to populate list of CollectionAgainst
/*
 * var cnfor; function getCnforList(domObj){ cnfor = getRowId(domObj,"cnfor");
 * var url="./cnCollection.do?submitName=getCNForDtls"; ajaxCallWithoutForm(url,
 * ajxRespForgetCnforList); } //Ajax call response to populate list of
 * CollectionAgainst function ajxRespForgetCnforList(ajaxResp){ if
 * (!isNull(ajaxResp)) { var content = document.getElementById('cnfor'+cnfor);
 * content.innerHTML = ""; $.each(ajaxResp, function(index, value) { var option;
 * option = document.createElement("option"); option.value = this.stdTypeCode;
 * option.appendChild(document.createTextNode(this.description));
 * content.appendChild(option); }); jQuery.unblockUI(); } }
 */

/**
 * TO get Todays deliverd CN details
 */
function getTodaysDeliverdConsgDtls() {
	// var cureentDate = document.getElementById("collectionDate").value;
	var loginOffId = document.getElementById("originOfficeId").value;
	var url = "./cnCollection.do?submitName=getTodaysDeliverdConsgDtls&originOfficeId="
			+ loginOffId;
	showProcessing();
	jQuery.ajax({
		url : url,
		success : function(req) {
			respFordeliverdConsgDtls(req);
		}
	});
}

function respFordeliverdConsgDtls(ajaxResp) {
	if (!isNull(ajaxResp)) {
		if (isJsonResponce(ajaxResp)) {
			return;
		}
		var cnCollection = eval('(' + ajaxResp + ')');
		if (!isNull(cnCollection.cnCollectionDtls.length)) {
			addCnCollectionRow();
			for ( var i = 0; i < cnCollection.cnCollectionDtls.length; i++) {
				document.getElementById("txnNo" + (i + 1)).value = cnCollection.cnCollectionDtls[i].txnNo;
				document.getElementById("cnNo" + (i + 1)).value = cnCollection.cnCollectionDtls[i].cnNo;
				document.getElementById("collectionType" + (i + 1)).value = cnCollection.cnCollectionDtls[i].collectionType;
				document.getElementById("consgIds" + (i + 1)).value = cnCollection.cnCollectionDtls[i].consgId;
				document.getElementById("collectionEntryId" + (i + 1)).value = cnCollection.cnCollectionDtls[i].entryId;
				document.getElementById("collectionID" + (i + 1)).value = cnCollection.cnCollectionDtls[i].collectionID;
				document.getElementById("receiptNo" + (i + 1)).value = cnCollection.cnCollectionDtls[i].receiptNo;
				if (!isNull(cnCollection.cnCollectionDtls[i].rcvdAmt))
					document.getElementById("rcvdAmt" + (i + 1)).value = cnCollection.cnCollectionDtls[i].rcvdAmt;
				// setAmountFormatZero(document.getElementById("rcvdAmt"+(i+1)));
				if (!isNull(cnCollection.cnCollectionDtls[i].tdsAmt))
					document.getElementById("tdsAmt" + (i + 1)).value = cnCollection.cnCollectionDtls[i].tdsAmt;
				if (!isNull(cnCollection.cnCollectionDtls[i].total))
					document.getElementById("total" + (i + 1)).value = cnCollection.cnCollectionDtls[i].total;
				// setAmountFormatZero(document.getElementById("tdsAmt"+(i+1)));
				if (!isNull(cnCollection.cnCollectionDtls[i].amount))
					document.getElementById("amount" + (i + 1)).value = cnCollection.cnCollectionDtls[i].amount;
				// setAmountFormatZero(document.getElementById("amount"+(i+1)));
				// setTotalAmt(i + 1);
				document.getElementById("mode" + (i + 1)).value = cnCollection.cnCollectionDtls[i].paymentModeId;
				document.getElementById("cnfor" + (i + 1)).value = cnCollection.cnCollectionDtls[i].cnfor;
				document.getElementById("chqNo" + (i + 1)).value = cnCollection.cnCollectionDtls[i].chqNo;
				document.getElementById("chqDate" + (i + 1)).value = cnCollection.cnCollectionDtls[i].chqDate;
				document.getElementById("bankName" + (i + 1)).value = cnCollection.cnCollectionDtls[i].bankName;
				document.getElementById("reasonId" + (i + 1)).value = cnCollection.cnCollectionDtls[i].reasonId;
				document.getElementById("cnDeliveryDt" + (i + 1)).value = cnCollection.cnCollectionDtls[i].consgDeliveryDate;
				document.getElementById("bankGL" + (i + 1)).value = cnCollection.cnCollectionDtls[i].bankGL;
				// document.getElementById("createdBy").value =
				// cnCollection.cnCollectionDtls[i].createdBy;
				// document.getElementById("updatedBy").value =
				// cnCollection.cnCollectionDtls[i].updatedBy;
				checkPaymentModeDtls(document.getElementById("mode" + (i + 1)));
				var collectionTypeVal = getDomElementById("collectionType"
						+ (i + 1)).value;
				if (collectionTypeVal == CN_FOR_LC
						|| collectionTypeVal == CN_FOR_COD) {
					var tdsAmtObj = document.getElementById("tdsAmt" + (i + 1));
					tdsAmtObj.readOnly = true;
					tdsAmtObj.setAttribute("tabindex", "-1");
				}

				if ((i + 1) != cnCollection.cnCollectionDtls.length)
					addCnCollectionRow();
			}
		} else {
			alert("No Consignment(s) Found For CN Collection");
		}
	}
	hideProcessing();
}

/**
 * To save Cn Collection details
 */
function saveOrUpdateCNCollection(action) {
	var isValid = true;
	if (action == 'save') {
		getDomElementById("status").value = STATUS_OPENED;
	} else if (action == 'submit') {
		isValid = validateCNGridDtls();
		getDomElementById("status").value = STATUS_SUBMITTED;
	}
	var isOneChecked = isAtleastOneRowChecked();
	if (isValid && isOneChecked) {
		showProcessing();
		dropdownEnable();// To enable all disabled drop down to SAVE
		var url = './cnCollection.do?submitName=saveOrUpdateCNCollection';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#cnCollectionForm").serialize(),
			success : function(req) {
				callSaveOrUpdateCNCollection(req);
			}
		});
	}
}
// ajax: call back function for saveOrUpdateCNCollection
function callSaveOrUpdateCNCollection(ajaxResp) {
	var cnCollection = eval('(' + ajaxResp + ')');
	var responsetext = jsonJqueryParser(ajaxResp);
	var error = responsetext[ERROR_FLAG];
	if (responsetext != null && error != null) {
		alert(error);
		hideProcessing();
	} else {
		if (cnCollection.status == STATUS_OPENED) {
			alert('Transaction(s) saved successfully.');
		} else if (cnCollection.status == STATUS_SUBMITTED) {
			alert('Transaction(s) submitted successfully.');
		} else {
			alert('Transaction(s) not saved. Please try again.');
		}
		cancleCnData();
		hideProcessing();
	}
}

/**
 * To submit cn collection details
 */
function submitCnCollection() {
	if (validateCNGridDtls()) {
		var url = './cnCollection.do?submitName=submitCnCollection';
		showProcessing();
		dropdownEnable();// To enable all disabled drop down to SUBMIT
		jQuery.ajax({
			url : url,
			data : jQuery("#cnCollectionForm").serialize(),
			success : function(req) {
				callsubmitCnCollection(req);
			}
		});
	}
}

function callsubmitCnCollection(ajaxResp) {
	var collectionDtlsTO = eval('(' + ajaxResp + ')');
	if (!isNull(ajaxResp)) {
		if (isJsonResponce(ajaxResp)) {
			return;
		}
	}
	if (!isNull(collectionDtlsTO.isSaved)
			&& collectionDtlsTO.isSaved == 'SUCCESS') {
		alert('CN Collection Submitted successfully.');
	} else {
		alert('CN Collection Can Not Submitted.');
	}
	hideProcessing();
	cancleCnData();
	/*
	 * var url = "./cnCollection.do?submitName=viewCNCollection";
	 * document.cnCollectionForm.action = url;
	 * document.cnCollectionForm.submit();
	 */
}

/**
 * To clear screen
 */
function cancleCnData() {
	var url = "./cnCollection.do?submitName=viewCNCollection";
	document.cnCollectionForm.action = url;
	document.cnCollectionForm.submit();
}

/**
 * validate CN collection grid row(s)
 * 
 * @returns {Boolean}
 */
function validateCNGridDtls() {
	for ( var i = 1; i < rowCount; i++) {
		if (getDomElementById("chk" + i).checked == true) {
			var mode = getDomElementById("mode" + i);
			var atLine = " at Line:" + i;
			/* Check madatory for mode. */
			if (mode.value == CHQ_MODE) {
				var cnfor = getDomElementById("cnfor" + i);
				var chqNo = getDomElementById("chqNo" + i);
				var chqDate = getDomElementById("chqDate" + i);
				var bankName = getDomElementById("bankName" + i);
				var bankGL = getDomElementById("bankGL" + i);
				if (isNull(cnfor.value)) {
					alert("Please Select CN For" + atLine);
					setTimeout(function() {
						cnfor.focus();
					}, 10);
					return false;
				}
				if (isNull(chqNo.value)) {
					alert("Please Provide Cheque No." + atLine);
					setTimeout(function() {
						chqNo.focus();
					}, 10);
					return false;
				}
				if (isNull(chqDate.value)) {
					alert("Please Provide Cheque Date" + atLine);
					setTimeout(function() {
						chqDate.focus();
					}, 10);
					return false;
				}
				if (isNull(bankName.value)) {
					alert("Please Provide Bank" + atLine);
					setTimeout(function() {
						bankName.focus();
					}, 10);
					return false;
				}

				// Added by Himal - COLLECTION FOR = Customer check
				if (!isNull(cnfor.value) && cnfor.value != COLL_FOR_CUST) {
					// Added by Himal - Bank GL
					if (isNull(bankGL.value)) {
						alert("Please Select Bank GL" + atLine);
						setTimeout(function() {
							bankGL.focus();
						}, 10);
						return false;
					}
				}
			}
		}
	}// END FOR LOOP
	return true;
}

/**
 * To validate total amount
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateTotalAmt(obj) {
	var rowId = getRowId(obj, "total");
	var amt = getDomElementById("amount" + rowId);
	var atLine = " at Line:" + rowId;
	if (obj.value != amt.value) {
		alert("Total Amount should equal to Bill Amount" + atLine);
		getDomElementById("tdsAmt" + rowId).value = "";
		obj.value = amount.value;
		getDomElementById("rcvdAmt" + rowId).value = amt.value;
		setTimeout(function() {
			getDomElementById("tdsAmt" + rowId).focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * Validate reason value
 * 
 * @param obj
 */
function validateReasons(obj) {
	var rowId = getRowId(obj, "reasonId");
	if (obj.value == PARTY_LETTER) {
		getDomElementById("rcvdAmt" + rowId).value = "";
		setTotalAmt(rowId);
	}
}

/**
 * To set Total Amount to grid
 * 
 * @param rowId
 */
function setTotalAmt(rowId) {
	var rcvdAmt = getDomElementById("rcvdAmt" + rowId).value;
	var tdsAmt = getDomElementById("tdsAmt" + rowId).value;

	rcvdAmt = (isNull(rcvdAmt)) ? 0 : parseFloat(rcvdAmt) * 100;
	tdsAmt = (isNull(tdsAmt)) ? 0 : parseFloat(tdsAmt) * 100;

	var totAmt = rcvdAmt + tdsAmt;
	totAmt /= 100;
	jQuery("#total" + rowId).val(totAmt);
	// validateTotalAmt(getDomElementById("total" + rowId));
}

/**
 * To check enter key navigation with integer allow
 * 
 * @param e
 * @param focusId
 * @returns {boolean}
 */
function checkNumWithEnterKey(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if (charCode > 31 && (charCode < 48 || charCode > 57)) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
		return false;
	} else {
		return true;
	}
}

/**
 * To check enter key navigation with decimal allow
 * 
 * @param e
 * @param focusId
 * @returns {boolean}
 */
function checkDecimalWithEnterKey(e, focusId) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox

	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode != 46) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
	} else {
		return true;
	}
}

/**
 * Enter key navigation for payment mode
 * 
 * @param e
 * @param rowId
 * @returns {Boolean}
 */
function enterKeyForPaymentMode(e, rowId) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = e.which; // firefox
	}

	if (charCode == 13) {
		var mode = getDomElementById("mode" + rowId).value;
		if (mode == CHQ_MODE) {
			document.getElementById("cnfor" + rowId).focus();
		} else {
			document.getElementById("reasonId" + rowId).focus();
		}
	} else {
		return true;
	}
}

/**
 * Enter key navigation for last element of row
 * 
 * @returns {Boolean}
 */
function lastObjEnterKeyNavi(e, rowId) {
	var charCode;
	if (window.event) {
		charCode = window.event.keyCode; // IE
	} else {
		charCode = e.which; // firefox
	}
	var nextRowId = parseInt(rowId) + 1;
	if (charCode == 13) {
		if (nextRowId == rowCount) {
			document.getElementById("saveBtn").focus();
		} else {
			document.getElementById("receiptNo" + nextRowId).focus();
		}
	} else {
		return true;
	}
}

/**
 * To check empty amount
 * 
 * @param domValue
 * @returns {Boolean}
 */
function isEmptyAmt(domValue) {
	if (!isNull(domValue)) {
		domValue = parseFloat(domValue).toFixed(2);
		if (domValue == "0.00") {
			return true;
		}
	} else if (isNull(domValue)) {
		return true;
	}
	return false;
}

/**
 * To select all check box
 * 
 * @param obj
 */
function selectAllCheckBox(obj) {
	if (obj.checked == true) {
		for ( var i = 1; i < rowCount; i++) {
			getDomElementById("chk" + i).checked = true;
			getDomElementById("isChecked" + i).value = "Y";
		}
	} else {
		for ( var i = 1; i < rowCount; i++) {
			getDomElementById("chk" + i).checked = false;
			getDomElementById("isChecked" + i).value = "N";
		}
	}
}

/**
 * To verfy is alteast one check box checked or not.
 * 
 * @returns {Boolean}
 */
function isAtleastOneRowChecked() {
	var flag = false;
	for ( var i = 1; i < rowCount; i++) {
		if (getDomElementById("chk" + i).checked == true) {
			flag = true;
			break;
		}
	}
	if (!flag) {
		alert("Please select atleast one row to save or submit.");
	}
	return flag;
}

/**
 * To validate checkbox whether is checked or not
 * 
 * @param obj
 */
function validateCheckbox(obj) {
	var rowId = getRowId(obj, "chk");
	if (obj.checked == true) {
		getDomElementById("isChecked" + rowId).value = "Y";
	} else {
		getDomElementById("isChecked" + rowId).value = "N";
	}
}

/**
 * To validate Received amount
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateRcvdAmt(obj) {
	var isValid = true;
	var rowId = getRowId(obj, "rcvdAmt");
	var amt = getDomElementById("amount" + rowId);
	var atLine = " at Line:" + rowId;
	if (parseFloat(obj.value) > parseFloat(amt.value)) {
		alert("Received Amount should not greater than Bill Amount" + atLine);
		// obj.value = "";
		getDomElementById("tdsAmt" + rowId).value = "";
		getDomElementById("total" + rowId).value = "";
		setTimeout(function() {
			obj.focus();
		}, 10);
		isValid = false;
	}
	if (isValid
			&& (getDomElementById("tdsAmt" + rowId).readOnly == true || !isNull(getDomElementById("tdsAmt"
					+ rowId).value))) {
		isValid = validateTotalAmt(getDomElementById("total" + rowId));
	}
	return isValid;
}

/**
 * To validate TDS amount
 * 
 * @param obj
 * @returns {Boolean}
 */
function validateTDSAmt(obj) {
	var rowId = getRowId(obj, "tdsAmt");
	var amt = getDomElementById("amount" + rowId);
	var atLine = " at Line:" + rowId;
	if (parseFloat(obj.value) > parseFloat(amt.value)) {
		alert("TDS Amount should not greater than Bill Amount" + atLine);
		obj.value = "";
		getDomElementById("total" + rowId).value = amt.value;
		getDomElementById("rcvdAmt" + rowId).value = amt.value;
		setTimeout(function() {
			obj.focus();
		}, 10);
		return false;
	}
	if (!isNull(!isNull(getDomElementById("rcvdAmt" + rowId).value))) {
		validateTotalAmt(getDomElementById("total" + rowId));
	}
	return true;
}

/**
 * To auto calculate received amount and total amount as per tds amount
 * 
 * @param obj
 */
function calcRcvdAmt(obj) {
	var rowId = getRowId(obj, "tdsAmt");
	var amount = getDomElementById("amount" + rowId);
	var rcvdAmt = getDomElementById("rcvdAmt" + rowId);
	if (!isNull(obj.value)) {
		rcvdAmt.value = (parseFloat(amount.value) * 100 - parseFloat(obj.value) * 100) / 100;
		setTotalAmt(rowId);
		validateTDSAmt(obj);
	} else {
		rcvdAmt.value = amount.value;
		getDomElementById("total" + rowId).value = amount.value;
	}
}

/**
 * To check COLLECTION FOR drop down value and validate bank gl field
 * 
 * @param collForObj
 */
function checkCollForDtls(collForObj) {
	var rowId = getRowId(collForObj, "cnfor");
	if (!isNull(collForObj)) {
		if (collForObj.value == COLL_FOR_FFCL) {
			getDomElementById("bankGL" + rowId).disabled = false;
		} else if (collForObj.value == COLL_FOR_CUST) {
			getDomElementById("bankGL" + rowId).value = "";
			getDomElementById("bankGL" + rowId).disabled = true;
		}
	}
}