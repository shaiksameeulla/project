var eachConsgWeightArr = new Array();
var ERROR_FLAG = "ERROR";

// Add Row Funtions..
var serialNo = 1;
var rowCount = 1;
$(document).ready(function() {
	var oTable = $('#collectionDetails').dataTable({
		"sScrollY" : "120",
		"sScrollX" : "100%",
		"sScrollXInner" : "110%",
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
 * isJsonResponce
 * 
 * @param ObjeResp
 * @returns {Boolean}
 */
function isJsonResponce(ObjeResp) {
	var responseText = null;
	try {
		responseText = jsonJqueryParser(ObjeResp);
	} catch (e) {

	}
	if (!isNull(responseText)) {
		var error = responseText[ERROR_FLAG];
		if (!isNull(error)) {
			alert(error);
			return true;
		}
	}
	return false;
}

function loadDefaultObjects() {
	checkCollectionMode(getDomElementById("collectionModeId"));
	addCollectionRow();
	loadCustomerName();
	getDomElementById("custName").focus();
}

function addCollectionRow() {

	$('#collectionDetails')
			.dataTable()
			.fnAddData(
					[
							serialNo
									+ '<input type="hidden" name="to.srNos" value ='
									+ serialNo
									+ ' readonly="true"/><input type="hidden" name="to.collectionEntryIds" id="collectionEntryIds'
									+ rowCount + '"/>',
							'<select  id="collectionAgainsts'
									+ rowCount
									+ '" name="to.collectionAgainsts" class="selectBox width120" onchange= "collectionAgainstView(this);" onkeypress="return callEnterKey(event,getDomElementById(\'billNos'
									+ rowCount
									+ '\'));"/><option value="B">BILL</option></select>',

							'<select id="billNos'
									+ rowCount
									+ '" name="to.billNos" class="selectBox width120" onchange="getBillAmount(this);" onkeypress="return callEnterKey(event,getDomElementById(\'billAmounts'
									+ rowCount
									+ '\'));"/><option value="">---Select---</option></select>',

							'<input type="text" id="billAmounts'
									+ rowCount
									+ '" name="to.billAmounts" class="txtbox width70 rightAlignment" size="11" onblur="setFractions(this,2);" onkeypress="return onlyDecimalAndEnterKeyNav(event,\'receivedAmounts'
									+ rowCount + '\');"/>',
							'<input type="text" name = "to.receivedAmounts" id="receivedAmounts'
									+ rowCount
									+ '" maxlength="10" class="txtbox width70 rightAlignment"  size="11" onkeypress="return onlyDecimalAndEnterKeyNav(event,\'tdsAmounts'
									+ rowCount
									+ '\');" onchange = "calculateTotalAmount(this,\'R\');validateRcvdAmt(this); calculateHeaderAmt(this);"/>',
							'<input type="text" name = "to.tdsAmounts" id="tdsAmounts'
									+ rowCount
									+ '"  class="txtbox width70 rightAlignment" maxlength="10" size="11"  onkeypress="return onlyDecimalAndEnterKeyNav(event,\'deductions'
									+ rowCount
									+ '\');"  onblur = "calculateTotalAmount(this,\'T\');validateTDSAmt(this);"  />',
							'<input type="text" name = "to.deductions" id="deductions'
									+ rowCount
									+ '" maxlength="10" class="txtbox width70 rightAlignment" size="11" onkeypress="return onlyUnsignedDecimalAndEnterKeyNav(event,\'totals'
									+ rowCount
									+ '\', this);" onblur = "calculateTotalAmount(this,\'D\');validateDeductionAmt(this,'
									+ rowCount + ')" />',
							'<input type="text" name = "to.totals" id="totals'
									+ rowCount
									+ '" maxlength="10" class="txtbox width70 rightAlignment" size="11"  onkeypress="return onlyDecimalAndEnterKeyNav(event,\'remarks'
									+ rowCount + '\');"/>',
							'<select id="reasonId'
									+ rowCount
									+ '" name="to.reasonIds" class="selectBox width145" onkeypress="return callEnterKey(event, document.getElementById(\'remarks'
									+ rowCount + '\'));">' + reasonOptions
									+ '</select>',
							'<input type="text" maxlength="30" name = "to.remarks" id="remarks'
									+ rowCount
									+ '"  class="txtbox width170" size="11" onkeypress = "checkEnterKeyForAddRow(event,'
									+ rowCount + ');" />' ]);
	rowCount++;
	serialNo++;
	getItemList(getDomElementById("collectionAgainsts" + (rowCount - 1)));
	getBillDeatils(rowCount - 1);
	validateReasonDropDown(rowCount - 1);
}

function addValidateBillCollectionRow() {

	$('#collectionDetails')
			.dataTable()
			.fnAddData(
					[
							serialNo
									+ '<input type="hidden" name="to.srNos" value ='
									+ serialNo
									+ ' readonly="true"/><input type="hidden" name="to.collectionEntryIds" id="collectionEntryIds'
									+ rowCount + '"/>',
							'<select  id="collectionAgainsts'
									+ rowCount
									+ '" name="to.collectionAgainsts"  value = ""  class="selectBox width145" onfocus="getItemList(this);" onchange= "collectionAgainstView(this);"/><option value="B">BILL</option></select>',
							'<input type="text" id="billNos'
									+ rowCount
									+ '" name="to.billNos" class="txtbox width70" size="11" onkeypress="return callEnterKeyAlphaNum(event,getDomElementById(\'billAmounts'
									+ rowCount
									+ '\'));" onchange="getBillAmount(this);" />',
							'<input type="text" id="billAmounts'
									+ rowCount
									+ '" name="to.billAmounts" class="txtbox width70" size="11" />',
							'<input type="text" name = "to.receivedAmounts" id="receivedAmounts'
									+ rowCount
									+ '"  class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" onblur = "calculateCorrectedTotal(this,\'R\');"/>',
							'<input type="text" name = "to.correctedRecvAmount" id="correctedRecvAmount'
									+ rowCount
									+ '"  class="txtbox width70" size="11" onkeypress="return onlyDecimal(event);" onblur = "calculateCorrectedTotal(this,\'R\');"/>',
							'<input type="text" name = "to.tdsAmounts" id="tdsAmounts'
									+ rowCount
									+ '"  class="txtbox width70" size="11"  onkeypress="return onlyDecimal(event);" onblur = "calculateCorrectedTotal(this,\'T\');"  />',
							'<input type="text" name = "to.correctedTDS" id="correctedTDS'
									+ rowCount
									+ '"  class="txtbox width70" size="11"  onkeypress="return onlyDecimal(event);" onblur = "calculateCorrectedTotal(this,\'T\');"  />',
							'<input type="text" name = "to.totals" id="totals'
									+ rowCount
									+ '"  class="txtbox width70" size="11" />',
							'<input type="text" name = "to.remarks" id="remarks'
									+ rowCount
									+ '"  class="txtbox width170" size="11" />', ]);
	rowCount++;
	serialNo++;
}

function loadBillValidateDefaultObjects() {
	var isCorrectionParam = document.getElementById("isCorrectionParam").value;
	var collectionModeId = document.getElementById("collectionModeId").value;
	if (!isNull(isCorrectionParam) && isCorrectionParam != 'Y'
			&& BC_MODE != collectionModeId) {
		inputDisable();
		dropdownDisable();
	}
	if (collStatus == 'V') {
		inputDisable();
		dropdownDisable();
	}
	if (isCorrectionParam == 'Y') {
		enableDisabledForCorrection(false);
	}
}

function addNewRow(domObj) {
	var rowNo = getRowId(domObj, "remarks");
	var nextRow = parseInt(rowNo) + 1;
	if (!isNull(document.getElementById("receivedAmounts" + rowNo).value)) {
		addCollectionRow();
		document.getElementById("collectionAgainsts" + nextRow).focus();
	}
}
function updateSrlNo(tableID) {
	try {
		var table = document.getElementById(tableID);
		for ( var i = 1; i < rowCount; i++) {
			var row = table.rows[i];
			var val = row.cells[1];
			val.innerHTML = i;
		}
	} catch (e) {
		// alert(e);
	}
}

// Ajax call to populate list of CollectionAgainst
var collectionAgainstsgridId;
function getItemList(domObj) {
	collectionAgainstsgridId = getRowId(domObj, "collectionAgainsts");
	var url = "./billCollection.do?submitName=getCollectionAgainstDtls";
	ajaxCallWithoutForm(url, ajxRespForCollectionAgainsts);
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

// Ajax call response to populate list of CollectionAgainst
function ajxRespForCollectionAgainsts(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = ajaxResp;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		var content = document.getElementById('collectionAgainsts'
				+ collectionAgainstsgridId);
		content.innerHTML = "";
		$.each(ajaxResp, function(index, value) {
			var option;
			option = document.createElement("option");
			option.value = this.stdTypeCode;
			option.appendChild(document.createTextNode(this.description));
			content.appendChild(option);
		});
		jQuery.unblockUI();
	}
}

function validateChqNumber(chqNo) {
	var isValidReturnVal = true;
	if (!isNull(chqNo.value)) {
		if (chqNo.value.length < 6 || chqNo.value.length > 6) {
			alert('Cheque No. length should be 6 character');
			chqNo.value = "";
			setTimeout(function() {
				chqNo.focus();
			}, 10);
			isValidReturnVal = false;
		}

	}
	return isValidReturnVal;
}

/**
 * To save or update bill collection and submit
 * 
 * @param action
 */
function saveOrUpdateBillCollection(action) {
	if (validateHeader() && validateGridDtls() && isHeaderAmtValid()) { // validateGridDetails
		showProcessing();
		if (action == "save") {
			getDomElementById("trxStatus").value = STATUS_SAVED;
		} else if (action == "submit") {
			getDomElementById("trxStatus").value = STATUS_SUBMITTED;
		}
		enableDisableBillGridDropDown(false);
		var url = './billCollection.do?submitName=saveOrUpdateCollection';
		jQuery.ajax({
			url : url,
			type : "POST",
			data : jQuery("#billCollectionForm").serialize(),
			success : function(req) {
				callSaveOrUpdateBillCollection(req);
			}
		});
	}
}

// call back function for save or update
function callSaveOrUpdateBillCollection(ajaxResp) {
	var responsetext = jsonJqueryParser(ajaxResp);
	var error = responsetext[ERROR_FLAG];
	if (responsetext != null && error != null) {
		alert(error);
		hideProcessing();
	} else {
		hideProcessing();
		var data = eval('(' + ajaxResp + ')');
		alert(data.transMsg);
		clearScreen();
	}
}

function validateHeader() {
	var collectionDate = getDomElementById("collectionDate");
	var custName = getDomElementById("custName");
	var custCode = getDomElementById("custCode");
	var collectionModeId = getDomElementById("collectionModeId");
	var chqNo = getDomElementById("chqNo");
	var chqDate = getDomElementById("chqDate");
	var bankName = getDomElementById("bankName");
	var bankGL = getDomElementById("bankGL");
	var amount = getDomElementById("amount");
	if (isNull(collectionDate.value)) {
		alert("Please provide Collection Date.");
		setTimeout(function() {
			collectionDate.focus();
		}, 10);
		return false;
	}
	if (isNull(custName.value)) {
		alert("Please provide Customer Name.");
		setTimeout(function() {
			custName.focus();
		}, 10);
		return false;
	}
	if (isNull(custCode.value)) {
		alert("Please provide Customer Code.");
		setTimeout(function() {
			custCode.focus();
		}, 10);
		return false;
	}
	if (isNull(collectionModeId.value)) {
		alert("Please provide Mode of Collection.");
		setTimeout(function() {
			collectionModeId.focus();
		}, 10);
		return false;
	}
	if (collectionModeId.value == BC_MODE) {
		if (isNull(chqNo.value)) {
			alert("Please provide Cheque Number.");
			setTimeout(function() {
				chqNo.focus();
			}, 10);
			return false;
		}
		if (isNull(chqDate.value)) {
			alert("Please provide Cheque Date.");
			setTimeout(function() {
				chqDate.focus();
			}, 10);
			return false;
		}
		if (isNull(bankName.value)) {
			alert("Please provide Bank Name.");
			setTimeout(function() {
				bankName.focus();
			}, 10);
			return false;
		}
		if (isNull(bankGL.value)) {
			alert("Please select BankGL.");
			setTimeout(function() {
				bankGL.focus();
			}, 10);
			return false;
		}
	}
	if (isEmptyAmt(amount.value)) {
		alert("Please provide Amount.");
		amount.value = "";
		setTimeout(function() {
			amount.focus();
		}, 10);
		return false;
	}

	return true;
}

/**
 * @Desc validate grid details
 * @returns {Boolean}
 */
function validateGridDetails() {
	for ( var i = 1; i < rowCount; i++) {
		if (validateSelectedRow(i)) {
			return true;
		}
	}
	alert("Please provide Atleast One received amount.");
	setTimeout(function() {
		billNos.focus();
	}, 10);
	return flase;
}

/**
 * @Desc validate selected Row by rowId
 * @param rowId
 * @returns {Boolean}
 */
function validateSelectedRow(rowId) {
	var receivedAmounts = getDomElementById("receivedAmounts" + rowId);
	if (!isNull(receivedAmounts.value)) {
		return true;
	}
	return false;
}

function validateAmount() {
	var receivedAmount = 0;
	var amount = parseFloat(getDomElementById("amount").value);
	for ( var i = 1; i < rowCount; i++) {
		if (!isNull(getDomElementById("receivedAmounts" + i).value))
			receivedAmount = parseFloat(receivedAmount)
					+ parseFloat(getDomElementById("receivedAmounts" + i).value);
	}
	if (amount != receivedAmount) {
		alert("Received amount total and header amount are not matched");
		return false;
	}
	return true;
}

function searchBillCollectionDetails() {
	var txnNo = document.getElementById("txnNo").value;
	if (!isNull(txnNo)) {
		url = './billCollection.do?submitName=searchBillCollectionDetails&txnNo='
				+ txnNo;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateBillCollectionDetails(req);
			}
		});
	} else {
		alert("Please provide Transaction number");
	}
}

function populateBillCollectionDetails(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = eval('(' + ajaxResp + ')');
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			var txNo = getDomElementById("txnNo");
			txNo.value = "";
			setTimeout(function() {
				txNo.focus();
			}, 10);
			hideProcessing();
			return;
		}
		var collectionDtlsTORes = eval('(' + ajaxResp + ')');
		var collectionDtlsTO = collectionDtlsTORes;
		document.getElementById("txnNo").value = collectionDtlsTO.txnNo;
		getDomElementById("txnNo").value = collectionDtlsTO.txnNo;
		getDomElementById("txnNo").readOnly = true;
		getDomElementById("collectionDate").value = collectionDtlsTO.collectionDate;
		getDomElementById("custName").value = collectionDtlsTO.custName;
		getDomElementById("custCode").value = collectionDtlsTO.custCode;
		getDomElementById("collectionModeId").value = collectionDtlsTO.collectionModeId;
		checkCollectionMode(getDomElementById("collectionModeId"));
		getDomElementById("chqNo").value = collectionDtlsTO.chqNo;
		getDomElementById("chqDate").value = collectionDtlsTO.chqDate;
		getDomElementById("bankName").value = collectionDtlsTO.bankName;
		getDomElementById("amount").value = parseFloat(collectionDtlsTO.amount)
				.toFixed(2);
		getDomElementById("collectionID").value = collectionDtlsTO.collectionID;
		getDomElementById("trxStatus").value = collectionDtlsTO.status;
		getDomElementById("custId").value = collectionDtlsTO.custId;
		getDomElementById("bankGL").value = collectionDtlsTO.bankGL;

		getDomElementById("createdBy").value = collectionDtlsTO.createdBy;
		getDomElementById("updatedBy").value = collectionDtlsTO.updatedBy;

		getBillDtls(getDomElementById("custId"));
		// Grid Details
		for ( var i = 0; i < collectionDtlsTO.billCollectionDetailTO.length; i++) {

			document.getElementById("collectionEntryIds" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].collectionEntryId;
			document.getElementById("collectionAgainsts" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].collectionAgainst;
			collectionAgainstView(document.getElementById("collectionAgainsts"
					+ (i + 1)));
			var oTable = $('#collectionDetails').dataTable();
			if (collectionDtlsTO.billCollectionDetailTO[i].collectionAgainst == 'B') {
				var newValue = '<select id="billNos'
						+ (i + 1)
						+ '" name="to.billNos" class="selectBox width120" onkeypress="return callEnterKey(event,getDomElementById(\'billAmounts'
						+ (i + 1)
						+ '\'));" /><option value="">---Select---</option></select>';
				oTable.fnUpdate(newValue, i, 2);
				var billAmt = collectionDtlsTO.billCollectionDetailTO[i].billAmount;
				var totalAmt = collectionDtlsTO.billCollectionDetailTO[i].total;
				if (collectionDtlsTO.status != 'O' || billAmt == totalAmt) {
					getBillDtlsForSubmit(
							collectionDtlsTO.billCollectionDetailTO[i].billNo,
							i + 1);
				} else {
					getBillDeatils(i + 1);
				}
			} else {
				var newValue = '<input type="text" id="billNos'
						+ (i + 1)
						+ '" name="to.billNos" class="txtbox width70" size="11" onkeypress="return callEnterKeyAlphaNum(event,getDomElementById(\'billAmounts'
						+ (i + 1) + '\'));" />';
				oTable.fnUpdate(newValue, i, 2);
			}
			document.getElementById("billNos" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].billNo;
			document.getElementById("billAmounts" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].billAmount
					.toFixed(2);
			document.getElementById("receivedAmounts" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].recvdAmt
					.toFixed(2);
			document.getElementById("tdsAmounts" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].tdsAmt
					.toFixed(2);
			document.getElementById("deductions" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].deduction
					.toFixed(2);
			validateReasonDropDown(i + 1);
			document.getElementById("totals" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].total
					.toFixed(2);
			document.getElementById("remarks" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].remarks;
			document.getElementById("reasonId" + (i + 1)).value = collectionDtlsTO.billCollectionDetailTO[i].reasonId;
			if (i + 1 < collectionDtlsTO.billCollectionDetailTO.length) {
				addCollectionRow();
			} else if (collectionDtlsTO.status == STATUS_SAVED) {
				addCollectionRow();
			}
		}
		if (collectionDtlsTO.status == STATUS_SUBMITTED
				|| collectionDtlsTO.status == STATUS_VALIDATED) {
			disableForSubmit();
		}
		getDomElementById("Find").disabled = true;
	}
	hideProcessing();
}

function submitBillCollection() {
	if (validateHeader() && validateGridDetails() && validateAmount()) {
		showProcessing();
		var url = './billCollection.do?submitName=submitBillCollection';
		jQuery.ajax({
			url : url,
			data : jQuery("#billCollectionForm").serialize(),
			success : function(req) {
				callsubmitBillCollection(req);
			}
		});
	}
}

/**
 * @param ajaxResp
 */
function callsubmitBillCollection(ajaxResp) {
	if (!isNull(ajaxResp)) {
		if (isJsonResponce()) {
			return;
		}
	}
	var collectionDtlsTO = eval('(' + ajaxResp + ')');
	if (!isNull(collectionDtlsTO.isSaved)
			&& collectionDtlsTO.isSaved == 'SUCCESS') {
		alert('Bill Collection Submitted successfully.');
	} else {
		alert('Bill Collection Submitted Unsuccessfully.');
	}
	jQuery.unblockUI();
	var url = "./billCollection.do?submitName=prepareBillCollectionPage";
	document.billCollectionForm.action = url;
	document.billCollectionForm.submit();
}

function collectionAgainstView(domObj) {
	var rowNo = getRowId(domObj, "collectionAgainsts");
	var collAgnstVal = document.getElementById("collectionAgainsts" + rowNo).value;
	var oTable = $('#collectionDetails').dataTable();
	if (collAgnstVal == 'B') {
		var newValue = '<select id="billNos'
				+ rowNo
				+ '" name="to.billNos" class="selectBox width120" onchange="getBillAmount(this);" onkeypress="return callEnterKey(event,getDomElementById(\'billAmounts'
				+ rowNo
				+ '\'));" /><option value="">---SELECT---</option></select>';

		var temp = parseInt(rowNo) - 1;
		oTable.fnUpdate(newValue, temp, 2);
		getBillDeatils(rowNo);
	} else {
		var newValue = '<input type="text" id="billNos'
				+ rowNo
				+ '" name="to.billNos" class="txtbox width70" size="11" onkeypress="return callEnterKeyAlphaNum(event,getDomElementById(\'billAmounts'
				+ rowNo + '\'));" />';
		var temp = parseInt(rowNo) - 1;
		oTable.fnUpdate(newValue, temp, 2);
	}
	if (collAgnstVal == 'B') {
		document.getElementById("totals" + rowNo).readOnly = true;
		document.getElementById("billNos" + rowNo).readOnly = false;
		document.getElementById("billAmounts" + rowNo).readOnly = true;
		document.getElementById("tdsAmounts" + rowNo).readOnly = false;
		document.getElementById("deductions" + rowNo).readOnly = false;
	}
	if (collAgnstVal == 'D') {
		document.getElementById("totals" + rowNo).readOnly = true;
		document.getElementById("billNos" + rowNo).readOnly = false;
		document.getElementById("billAmounts" + rowNo).readOnly = false;
		document.getElementById("tdsAmounts" + rowNo).readOnly = false;
		document.getElementById("deductions" + rowNo).readOnly = false;
	}
	if (collAgnstVal == 'C') {
		document.getElementById("totals" + rowNo).readOnly = true;
		document.getElementById("billNos" + rowNo).readOnly = false;
		document.getElementById("billAmounts" + rowNo).readOnly = false;
		document.getElementById("tdsAmounts" + rowNo).readOnly = false;
		document.getElementById("deductions" + rowNo).readOnly = false;
	}
	if (collAgnstVal == 'O') {
		document.getElementById("billNos" + rowNo).value = "";
		document.getElementById("billNos" + rowNo).readOnly = true;
		document.getElementById("billAmounts" + rowNo).value = "";
		document.getElementById("billAmounts" + rowNo).readOnly = true;
		document.getElementById("tdsAmounts" + rowNo).value = "";
		document.getElementById("tdsAmounts" + rowNo).readOnly = true;
		document.getElementById("deductions" + rowNo).value = "";
		document.getElementById("deductions" + rowNo).readOnly = true;
		document.getElementById("totals" + rowNo).readOnly = true;
		calculateTotalAmount(getDomElementById("receivedAmounts" + rowNo), 'R');
	}
	setTimeout(function() {
		getDomElementById("collectionAgainsts" + rowNo).focus();
	}, 10);
}

/**
 * @param domObj
 * @param id =
 *            R,T,D
 */
function calculateTotalAmount(domObj, id) {
	if (!isNull(domObj.value) || id == 'D') {
		var total = 0;
		var rowNo = 0;
		var isCalcDedcReq = true;
		if (id == 'R') {
			rowNo = getRowId(domObj, "receivedAmounts");
		}
		if (id == 'T') {
			rowNo = getRowId(domObj, "tdsAmounts");
		}
		if (id == 'D') {
			rowNo = getRowId(domObj, "deductions");
			isCalcDedcReq = false;
		}
		if (!isEmptyAmt(document.getElementById("receivedAmounts" + rowNo).value)) {
			var rcvdAmt = document.getElementById("receivedAmounts" + rowNo);
			total = parseFloat(total) + parseFloat(rcvdAmt.value);
			rcvdAmt.value = parseFloat(rcvdAmt.value).toFixed(2);
			if (isCalcDedcReq) {
				calcDeductionAmt(rowNo);
			}
		}
		if (!isEmptyAmt(document.getElementById("tdsAmounts" + rowNo).value)) {
			var tdsAmt = document.getElementById("tdsAmounts" + rowNo);
			total = parseFloat(total) + parseFloat(tdsAmt.value);
			tdsAmt.value = parseFloat(tdsAmt.value).toFixed(2);
			if (isCalcDedcReq) {
				calcDeductionAmt(rowNo);
			}
		}
		if (!isEmptyAmt(document.getElementById("deductions" + rowNo).value)) {
			var deductionAmt = document.getElementById("deductions" + rowNo);
			if (deductionAmt.value == "-") {
				deductionAmt.value = "0.00";
			}
			total = parseFloat(total) + parseFloat(deductionAmt.value);
			deductionAmt.value = parseFloat(deductionAmt.value).toFixed(2);
		}
		document.getElementById("totals" + rowNo).value = total.toFixed(2);
		document.getElementById("totals" + rowNo).readOnly = true;
	}
}

/**
 * To calculate deduction amount based on rcvd and TDS amount.
 * 
 * @param rowId
 */
function calcDeductionAmt(rowId) {
	var collAgainst = getDomElementById("collectionAgainsts" + rowId).value;
	var isOnAcc = (collAgainst == COLL_AGAINST_ON_ACCOUNT);
	if (!isOnAcc) {
		var deduction = getDomElementById("deductions" + rowId);
		var tds = getDomElementById("tdsAmounts" + rowId);
		var rcvd = getDomElementById("receivedAmounts" + rowId);
		var billAmt = getDomElementById("billAmounts" + rowId);

		tds.value = parseFloat((isNull(tds.value)) ? "0.0" : tds.value)
				.toFixed(2);
		rcvd.value = parseFloat((isNull(rcvd.value)) ? "0.0" : rcvd.value)
				.toFixed(2);
		billAmt.value = parseFloat(
				(isNull(billAmt.value)) ? "0.0" : billAmt.value).toFixed(2);
		deduction.value = parseFloat(
				(parseFloat(billAmt.value) * 100 - parseFloat(parseFloat(tds.value)
						+ parseFloat(rcvd.value)) * 100) / 100).toFixed(2);
		validateDeductionAmt(deduction, rowId);
	}
}

function calculateCorrectedTotal(domObj, id) {
	var total = 0.0;
	var rowNo = "";
	if (id == 'R') {
		rowNo = getRowId(domObj, "correctedRecvAmount");
	}
	if (id == 'T') {
		rowNo = getRowId(domObj, "correctedTDS");
	}
	if (id == 'D') {
		rowNo = getRowId(domObj, "deduction");
	}

	if (!isEmptyAmt(document.getElementById("correctedRecvAmount" + rowNo).value)) {
		var crtdRcvdAmt = document
				.getElementById("correctedRecvAmount" + rowNo);
		total = parseFloat(total) + parseFloat(crtdRcvdAmt.value);
		crtdRcvdAmt.value = parseFloat(crtdRcvdAmt.value).toFixed(2);
	} else {
		var crtdRcvdAmt = document
				.getElementById("correctedRecvAmount" + rowNo);
		crtdRcvdAmt.value = 0.0;
		total = parseFloat(total) + parseFloat(crtdRcvdAmt.value);
		crtdRcvdAmt.value = parseFloat(crtdRcvdAmt.value).toFixed(2);
	}

	if (!isEmptyAmt(document.getElementById("correctedTDS" + rowNo).value)) {
		var crtdTDS = document.getElementById("correctedTDS" + rowNo);
		total = parseFloat(total) + parseFloat(crtdTDS.value);
		crtdTDS.value = parseFloat(crtdTDS.value).toFixed(2);
	} else {
		var crtdTDS = document.getElementById("correctedTDS" + rowNo);
		crtdTDS.value = 0.0;
		total = parseFloat(total) + parseFloat(crtdTDS.value);
		crtdTDS.value = parseFloat(crtdTDS.value).toFixed(2);
	}

	if (!isNull(document.getElementById("deduction" + rowNo))) {
		if (!isEmptyAmt(document.getElementById("deduction" + rowNo).value)) {
			var deduction = document.getElementById("deduction" + rowNo);
			total = parseFloat(total) + parseFloat(deduction.value);
			deduction.value = parseFloat(deduction.value).toFixed(2);
		} else {
			var deduction = document.getElementById("deduction" + rowNo);
			deduction.value = 0.0;
			total = parseFloat(total) + parseFloat(deduction.value);
			deduction.value = parseFloat(deduction.value).toFixed(2);
		}
	}

	document.getElementById("totals" + rowNo).value = parseFloat(total)
			.toFixed(2);
	document.getElementById("totals" + rowNo).readOnly = true;
}

function cancleData() {
	var collectionID = document.getElementById("collectionID").value;
	if (isNull(collectionID)) {
		if (confirm("Do you want to cancel ?")) {
			clearScreen();
		}
	} else {
		var status = getDomElementById("trxStatus").value;
		if (status == STATUS_SAVED) { // SAVED
			clearScreen();
		} else { // SUBMITTED, VALIDATED
			clearScreen();
		}
	}
}

function validateTotalAmount() {
	var headerAmount = getDomElementById("amount").value;
	var totalAmount = 0;
	for ( var i = 1; i < serialNo; i++) {
		totalAmount = parseFloat(totalAmount)
				+ parseFloat(document.getElementById("totals" + i).value);
	}
	if (headerAmount == totalAmount) {
		return true;
	}
	return false;
}

function getCustCode() {
	var selectedName = jQuery("#custName").val();
	if (!isNull(selectedName)) {
		$
				.each(
						data,
						function(index, customer) {
							var txtCustName = selectedName.substring(0,
									selectedName.lastIndexOf("("));
							var txtCustCode = selectedName.substring(
									selectedName.lastIndexOf("(") + 1,
									selectedName.lastIndexOf(")"));
							if (txtCustName == (customer + " ")
									&& trimString(txtCustCode) == trimString(custCodeArr[index])) {
								jQuery("#custCode").val(custCodeArr[index]);
								jQuery("#custId").val(custIDArr[index]);
								jQuery("#custName").val(customer);
								getBillDtls(getDomElementById("custId"));
								return false;
							}
						});
	} else {
		jQuery("#custCode").val("");
		jQuery("#custId").val("");
		billOptions = "";
	}
}

/**
 * To validate collection
 * 
 * @returns {Boolean}
 */
function ValidateCollection() {
	var url = './billCollection.do?submitName=validateCollection';
	var isCorrectionParam = getDomElementById("isCorrectionParam").value;
	var isValid = true;
	if (isCorrectionParam == "Y") {
		if (!validateHeaderForValidation() || !validateGridForValidation()) {
			isValid = false;
		}
	}
	if (isValid) {
		getDomElementById("validateBtn").disabled = true;
		showProcessing();
		enableOrDisableDropDown(false);
		enableOrDisableGridDropDown(false);
		jQuery.ajax({
			url : url,
			data : jQuery("#billCollectionForm").serialize(),
			success : function(req) {
				callValidateCollection(req);
			}
		});
	}
}

function callValidateCollection(ajaxResp) {
	getDomElementById("validateBtn").disabled = false;
	var collectionDtlsTO = eval('(' + ajaxResp + ')');
	if (!isNull(collectionDtlsTO.isSaved)
			&& collectionDtlsTO.isSaved == 'SUCCESS') {
		alert('Collection validated successfully.');
	} else {
		alert('Collection not validated.');
	}
	hideProcessing();
	if (!isNull(screenName) && screenName == 'bulkCollectionValidation') {
		window.opener.searchCollectionDetailsForBulkValidation();
	}
	else {
		window.opener.searchCollectionDetlsForValidation();
	}
	self.close();
}

function correction() {
	var txnNo = getDomElementById("txnNo").value;
	var collectionType = getDomElementById("collectionType").value;
	var officeId = getDomElementById("originOfficeId").value;
	var url = "./billCollection.do?submitName=viewCollectionEntryDtls&txnNo="
			+ txnNo + "&collectionType=" + collectionType
			+ "&isCorrectionParam=Y" + "&officeId=" + officeId;
	document.billCollectionForm.action = url;
	document.billCollectionForm.submit();
}

function checkCollectionMode(obj) {
	var collType = obj.value;
	if (collType == BC_MODE) {
		$('#chqNo').removeAttr("readonly");
		$("#bankName").removeAttr("readonly");
		getDomElementById("bankGL").disabled = false;
		$("#calendar").show();
	} else {
		jQuery("#chqNo").val("");
		jQuery("#bankName").val("");
		jQuery("#chqNo").attr("readonly", true);
		jQuery("#bankName").attr("readonly", true);
		getDomElementById("bankGL").value = "";
		getDomElementById("bankGL").disabled = true;
		$("#calendar").hide();
	}
}

function closePage() {
	self.close();
}

function getBillDeatils(rowId) {
	var content = document.getElementById('billNos' + rowId);
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("---SELECT---"));
	content.appendChild(defOption);
	for ( var i = 0; i < billOptions.length; i++) {
		defOption = document.createElement("option");
		defOption.value = billOptions[i].invoiceNumber;
		defOption.appendChild(document
				.createTextNode(billOptions[i].invoiceNumber));
		content.appendChild(defOption);
	}
}

/**
 * To get bill details after submit txn.
 * 
 * @param billNo
 * @param rowId
 */
function getBillDtlsForSubmit(billNo, rowId) {
	var content = document.getElementById('billNos' + rowId);
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = billNo;
	defOption.appendChild(document.createTextNode(billNo));
	content.appendChild(defOption);
}

/**
 * To get bill details
 * 
 * @param obj
 */
function getBillDtls(obj) {
	if (!isNull(obj.value)) {
		var originOfficeId = getDomElementById("originOfficeId").value;
		var url = "./billCollection.do?submitName=getBillDtls" + "&custId="
				+ obj.value + "&officeId=" + originOfficeId;
		showProcessing();
		jQuery.ajax({
			url : url,
			type : "POST",
			dataType : "json",
			success : function(data) {
				ajaxRespForBillDtls(data);
			},
			async : true
		});
	}
}
// call back method for bill details
function ajaxRespForBillDtls(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responseText = ajaxResp;
		var error = responseText[ERROR_FLAG];
		if (responseText != null && error != null) {
			alert(error);
			return;
		}
		billOptions = eval(ajaxResp);
		for ( var i = 1; i < rowCount; i++) {
			var collAgainst = getDomElementById("collectionAgainsts" + i).value;
			if (collAgainst == COLL_AGAINST_BILL) {
				if (isNull(getDomElementById("billNos" + i).value)
						&& isNull(getDomElementById("billAmounts" + i).value)) {
					getBillDeatils(i);
					jQuery("#billAmounts" + i).val("");
					getDomElementById("billAmounts" + i).readOnly = true;
				}
			}
		}
	} else {
		clearBillNoInGrid();
		billOptions = "";
	}
	hideProcessing();
}

/**
 * To clear all bill drop down in grid
 */
function clearBillNoInGrid() {
	for ( var i = 1; i < rowCount; i++) {
		if (getDomElementById("collectionAgainsts" + i).value == "B") {
			clearBillDropDown(i);
			clearBillCollectionRow(i);
		}
	}
}

/**
 * To clear bill drop down by rowId
 * 
 * @param rowId
 */
function clearBillDropDown(rowId) {
	var content = document.getElementById("billNos" + rowId);
	content.innerHTML = "";
	var defOption = document.createElement("option");
	defOption.value = "";
	defOption.appendChild(document.createTextNode("-- SELECT --"));
	content.appendChild(defOption);
}

function getBillAmount(obj) {
	var rowId = getRowId(obj, "billNos");
	if (!isNull(obj.value) && !isDuplicateBillNo(obj)) {
		for ( var i = 0; i < billOptions.length; i++) {
			if (obj.value == billOptions[i].invoiceNumber) {
				jQuery("#billAmounts" + rowId).val(
						billOptions[i].grandTotalRoundedOff.toFixed(2));
				getDomElementById("billAmounts" + rowId).readOnly = true;
			}
		}
	} else {
		jQuery("#billAmounts" + rowId).val("");
		getDomElementById("billAmounts" + rowId).readOnly = false;
	}
}
function fnEnterKeyNav(e, colModeObj) {
	if (!isNull(colModeObj.value)) {
		var key;
		if (window.event)
			key = window.event.keyCode; // IE
		else
			key = e.which; // firefox
		if (key == 13) {
			if (colModeObj.value == BC_MODE) {
				getDomElementById("chqNo").focus();
			} else {
				getDomElementById("collectionAgainsts1").focus();
			}
		}
	}
}
function focusCalendar(e) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var chqDateObj = getDomElementById("chqDate");
		if (isNull(chqDateObj.value)) {
			getDomElementById("calendarImg").focus();
		} else {
			getDomElementById("bankName").focus();
		}
	}
}

/**
 * To validate row by rowId
 * 
 * @param rowId
 * @returns {Boolean}
 */
function isValidRow(rowId) {
	var receivedAmounts = getDomElementById("receivedAmounts" + rowId);
	if (isEmptyAmt(receivedAmounts.value)) {
		alert("Please provide received amount.");
		setTimeout(function() {
			receivedAmounts.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * To validate row by rowId with Line No.
 * 
 * @param rowId
 * @returns {Boolean}
 */
function isValidRowWithLineNo(rowId) {
	var receivedAmounts = getDomElementById("receivedAmounts" + rowId);
	var reason = getDomElementById("reasonId" + rowId);
	if (isEmptyAmt(receivedAmounts.value)) {
		alert("Please provide received amount at line:" + rowId);
		receivedAmounts.value = "";
		setTimeout(function() {
			receivedAmounts.focus();
		}, 10);
		return false;
	}
	if (reason.disabled == false && isNull(reason.value)) {
		alert("Please select reason at line:" + rowId);
		setTimeout(function() {
			reason.focus();
		}, 10);
		return false;
	}
	return true;
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
 * To check enter key navigation
 * 
 * @param e
 * @param rowId
 * @returns {Boolean}
 */
function checkEnterKeyForAddRow(e, rowId) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		checkMandatoryForAdd(rowId);
		// return false;
	}
}

/**
 * To check mandatory field for adding new row
 * 
 * @param rowId
 */
function checkMandatoryForAdd(rowId) {
	var isLast = isLastRow(rowId);
	if (isLast && isValidRow(rowId)) {
		addCollectionRow();
		setTimeout(function() {
			getDomElementById("collectionAgainsts" + (rowCount - 1)).focus();
		}, 10);
	}
	if (!isLast) {
		setTimeout(function() {
			getDomElementById("collectionAgainsts" + (rowId + 1)).focus();
		}, 10);
	}
}

/**
 * To check whether it is last row
 * 
 * @param rowId
 * @returns {Boolean}
 */
function isLastRow(rowId) {
	if (rowCount == (parseInt(rowId) + 1)) {
		return true;
	}
	return false;
}

/**
 * To clear screen
 */
function clearScreen() {
	var url = "./billCollection.do?submitName=prepareBillCollectionPage";
	document.billCollectionForm.action = url;
	document.billCollectionForm.submit();
}

/**
 * To validate grid details for save and submit
 * 
 * @returns {Boolean}
 */
function validateGridDtls() {
	for ( var i = 1; i < rowCount; i++) {
		if (i != 1 && i == rowCount - 1) {
			continue;
		}
		if (!isValidRowWithLineNo(i)) {
			return false;
		}
	}
	return true;
}

/**
 * To disable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonDisabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", true);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).removeAttr("tabindex");
	jQuery("#" + btnName).addClass("btnintformbigdis");
}

/**
 * To enable button
 * 
 * @param btnName
 * @param styleclass
 */
function buttonEnabled(btnName, styleclass) {
	jQuery("#" + btnName).attr("disabled", false);
	jQuery("#" + btnName).removeClass(styleclass);
	jQuery("#" + btnName).removeAttr("tabindex");
	jQuery("#" + btnName).addClass("btnintform");
}

/**
 * To disabled for submit
 */
function disableForSubmit() {
	disableAll();
	buttonDisabled("saveBtn", "btnintform");
	buttonDisabled("submitBtn", "btnintform");
	buttonEnabled("cancelBtn", "btnintformbigdis");
}

/**
 * To enable or disable drop down on screen
 * 
 * @param flag
 */
function enableOrDisableDropDown(flag) {
	getDomElementById("collectionModeId").disabled = flag;
	getDomElementById("bankGL").disabled = flag;
}

/**
 * To enable or disable grid drop downs and other elements
 * 
 * @param flag
 */
function enableOrDisableGridDropDown(flag) {
	var tbl = document.getElementById('billCollectionValidate');
	var totalRowCount = tbl.rows.length - 1;
	var collectionType = getDomElementById("collectionType").value;
	for ( var i = 1; i <= totalRowCount; i++) {
		if (collectionType == BILL_TYPE) {
			if (!isNull(getDomElementById("collectionAgainsts" + i))) {
				getDomElementById("collectionAgainsts" + i).disabled = flag;
			}
		}
		getDomElementById("reason" + i).disabled = flag;
	}
}

/**
 * To enable or disabled for correction
 * 
 * @param flag
 */
function enableDisabledForCorrection(flag) {
	getDomElementById("txnNo").readOnly = true;
	getDomElementById("collectionDate").readOnly = true;
	getDomElementById("custName").readOnly = true;
	getDomElementById("custCode").readOnly = true;
	getDomElementById("chqNo").readOnly = true;
	getDomElementById("chqDate").readOnly = true;
	getDomElementById("bankName").readOnly = true;
	getDomElementById("amount").readOnly = true;
	getDomElementById("collectionModeId").disabled = true;
	getDomElementById("bankGL").disabled = true;
}

/**
 * To validate header amount and grid total amount
 * 
 * @returns {Boolean}
 */
function isHeaderAmtValid() {
	var amount = getDomElementById("amount");
	var totalGridAmt = 0;
	for ( var i = 1; i < rowCount; i++) {
		var totalAmt = getDomElementById("receivedAmounts" + i).value;
		if (!isEmptyAmt(totalAmt))
			totalGridAmt += parseFloat(totalAmt);
	}
	if (amount.value == totalGridAmt) {
		return true;
	} else {
		alert("Header amount and total in grid mismatch.");
		amount.value = "";
		setTimeout(function() {
			amount.focus();
		}, 10);
		return false;
	}
}

/**
 * To enable or disable bill grid drop down during save and submit
 * 
 * @param flag
 */
function enableDisableBillGridDropDown(flag) {
	for ( var i = 1; i < rowCount; i++) {
		getDomElementById("reasonId" + i).disabled = flag;
	}
}

/**
 * To validate deduction amt for reason element
 * 
 * @param obj
 * @param rowId
 */
function validateDeductionAmt(obj, rowId) {
	if (obj.value == "-" || isNull(obj.value)) {
		obj.value = "0.00";
	}
	var totalAmt = getDomElementById("totals" + rowId);
	var billAmt = getDomElementById("billAmounts" + rowId);
	if (!isEmptyAmt(billAmt.value)) {
		calculateTotalAmount(obj, 'D');
		if (parseFloat(totalAmt.value) > parseFloat(billAmt.value)
				|| isEmptyAmt(totalAmt.value) || parseFloat(totalAmt.value) < 0) {
			alert("Total amount should not greater than bill amount or zero or negative.");
			var rcvdAmt = getDomElementById("receivedAmounts" + rowId);
			obj.value = "";
			totalAmt.value = "";
			getDomElementById("tdsAmounts" + rowId).value = "";
			rcvdAmt.value = "";
			setTimeout(function() {
				rcvdAmt.focus();
			}, 10);
		}
	}
	if (isEmptyAmt(obj.value)) {
		getDomElementById("reasonId" + rowId).disabled = true;
	} else {
		getDomElementById("reasonId" + rowId).disabled = false;
	}
}

/**
 * To validate reason drop down
 * 
 * @param rowId
 */
function validateReasonDropDown(rowId) {
	validateDeductionAmt(getDomElementById("deductions" + rowId), rowId);
}

/**
 * To validate duplicate bill no. in grid.
 * 
 * @param obj
 * @returns {Boolean}
 */
function isDuplicateBillNo(obj) {
	var rowId = getRowId(obj, "billNos");
	for ( var i = 1; i < rowCount; i++) {
		if (rowId == i)
			continue;
		var billNoObj = getDomElementById("billNos" + i);
		if (!isNull(billNoObj) && obj.value == billNoObj.value) {
			alert("Duplicate Bill No. " + obj.value + " at Line:" + i);
			obj.value = "";
			setTimeout(function() {
				obj.focus();
			}, 10);
			return true;
		}
	}
	return false;
}

/**
 * To validate received amount
 * 
 * @param obj
 */
function validateRcvdAmt(obj) {
	if (!isNull(obj.value)) {
		var rowId = getRowId(obj, "receivedAmounts");
		var billAmt = getDomElementById("billAmounts" + rowId);
		if (!isEmptyAmt(billAmt.value)) {
			billAmt.value = parseFloat(billAmt.value).toFixed(2);
			var totalAmt = getDomElementById("totals" + rowId);
			calculateTotalAmount(obj, 'R');
			if (parseFloat(totalAmt.value) > parseFloat(billAmt.value)
					|| isEmptyAmt(totalAmt.value)
					|| parseFloat(totalAmt.value) < 0) {
				alert("Total amount should not greater than bill amount or zero or negative.");
				obj.value = "";
				totalAmt.value = "";
				getDomElementById("tdsAmounts" + rowId).value = "";
				getDomElementById("deductions" + rowId).value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return true;
			}
		}
		if (isEmptyAmt(obj.value)) {
			obj.value = 0;
		}
		obj.value = parseFloat(obj.value).toFixed(2);
	}
}

/**
 * To validate TDS amount
 * 
 * @param obj
 */
function validateTDSAmt(obj) {
	if (!isNull(obj.value)) {
		var rowId = getRowId(obj, "tdsAmounts");
		var billAmt = getDomElementById("billAmounts" + rowId);
		if (!isEmptyAmt(billAmt.value)) {
			billAmt.value = parseFloat(billAmt.value).toFixed(2);
			var totalAmt = getDomElementById("totals" + rowId);
			calculateTotalAmount(obj, 'T');
			if (parseFloat(totalAmt.value) > parseFloat(billAmt.value)
					|| isEmptyAmt(totalAmt.value)
					|| parseFloat(totalAmt.value) < 0) {
				alert("Total amount should not greater than bill amount or zero or negative.");
				obj.value = "";
				totalAmt.value = "";
				getDomElementById("deductions" + rowId).value = "";
				getDomElementById("receivedAmounts" + rowId).value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return true;
			}
		}
		if (isEmptyAmt(obj.value)) {
			obj.value = 0;
		}
		obj.value = parseFloat(obj.value).toFixed(2);
	}
}

/**
 * To validate Header amount
 * 
 * @param obj
 */
function validateHeaderAmt(obj) {
	if (!isEmptyAmt(obj.value)) {
		obj.value = parseFloat(obj.value).toFixed(2);
	} else {
		obj.value = 0.0;
		obj.value = parseFloat(obj.value).toFixed(2);
	}
}

/**
 * To validate header for validation during correction
 * 
 * @returns {Boolean}
 */
function validateHeaderForValidation() {
	/*
	 * var corrAmount = getDomElementById("corrAmount"); if
	 * (isEmptyAmt(corrAmount.value)) {
	 * 
	 * alert("Please enter valid corrected amount in header"); corrAmount.value =
	 * ""; setTimeout(function() { corrAmount.focus(); }, 10); return false;
	 * 
	 * corrAmount.value = 0.0; }
	 */
	return true;
}
/**
 * To validate grid for validation during correction
 * 
 * @returns {Boolean}
 */
function validateGridForValidation() {
	var tbl = getDomElementById('billCollectionValidate');
	var totalRowCount = tbl.rows.length - 1;
	var totalGridAmt = 0.0;
	var corrAmt = getDomElementById("corrAmount");
	for ( var i = 1; i <= totalRowCount; i++) {
		if (!isValidRowForValidation(i)) {
			return false;
		}
		var totals = getDomElementById("totals" + i).value;
		if (isEmptyAmt(totals))
			totals = 0.0;
		totalGridAmt = parseFloat(totalGridAmt) + parseFloat(totals);
		corrAmt.value = totalGridAmt;
	}
	/*
	 * if (totalGridAmt != corrAmt.value) { alert("Header corrected amount and
	 * total break up in grid do not match."); corrAmt.value = "";
	 * setTimeout(function() { corrAmt.focus(); }, 10); return false; }
	 */
	return true;
}

/**
 * To validate row in grid for validation during correction
 * 
 * @param rowId
 * @returns {Boolean}
 */
function isValidRowForValidation(rowId) {
	var crtdRecvAmt = getDomElementById("correctedRecvAmount" + rowId);
	var crtdTDS = getDomElementById("correctedTDS" + rowId);
	var deduction = getDomElementById("deduction" + rowId);
	var reason = getDomElementById("reason" + rowId);
	if (isEmptyAmt(crtdRecvAmt.value)) {
		/*
		 * alert("Please enter valid corrected received amount at line:" +
		 * rowId); crtdRecvAmt.value = ""; setTimeout(function() {
		 * crtdRecvAmt.focus(); }, 10); return false;
		 */
		crtdRecvAmt.value = 0.0;
	}
	/*
	 * if (isEmptyAmt(crtdTDS.value)) { alert("Please enter valid corrected TDS
	 * amount at line:" + rowId); crtdTDS.value = ""; setTimeout(function() {
	 * crtdTDS.focus(); }, 10); return false; }
	 */
	if (isEmptyAmt(crtdTDS.value)) {
		crtdTDS.value = 0.0;
	}

	// Deduction and Reason
	if (!isNull(deduction) && isEmptyAmt(deduction.value)) {
		deduction.value = 0.0;
		reason.value = "";
	} else if (!isNull(deduction) && !isEmptyAmt(deduction.value)) {
		if (!isNull(reason) && isNull(reason.value)) {
			alert("Please select reason at line:" + rowId);
			reason.value = "";
			setTimeout(function() {
				reason.focus();
			}, 10);
			return false;
		}
	}
	calculateCorrectedTotal(crtdRecvAmt, 'R');
	return true;
}

/**
 * To clear bill collection row by its row id
 */
function clearBillCollectionRow(rowId) {
	getDomElementById("collectionAgainsts" + rowId).value = "";
	getDomElementById("billNos" + rowId).value = "";
	getDomElementById("billAmounts" + rowId).value = "";
	getDomElementById("receivedAmounts" + rowId).value = "";
	getDomElementById("tdsAmounts" + rowId).value = "";
	getDomElementById("deductions" + rowId).value = "";
	getDomElementById("totals" + rowId).value = "";
	getDomElementById("reasonId" + rowId).value = "";
	getDomElementById("remarks" + rowId).value = "";
}

/**
 * Only Unsinged Decimal (+/-) allowed with enter key navigation.
 * 
 * @param e
 * @param focusId
 * @param obj
 * @returns {Boolean}
 */
function onlyUnsignedDecimalAndEnterKeyNav(e, focusId, obj) {
	var charCode;
	if (window.event)
		charCode = window.event.keyCode; // IE
	else
		charCode = e.which; // firefox
	// 46 . period
	// 45 - hyphen
	if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode != 46
			&& charCode != 45) {
		return false;
	} else if (charCode == 13) {
		document.getElementById(focusId).focus();
	} else {
		if (charCode == 45 && obj.value.length != 0) {
			return false;
		}
		return true;
	}
}

function setFractions(obj, digits) {
	if (!isNull(obj.value))
		obj.value = parseFloat(obj.value).toFixed(digits);
}

function calculateHeaderAmt(obj) {
	var amount = getDomElementById("amount");
	/*var totalGridAmt;
	if(isEmptyAmt(amount.value)){
		totalGridAmt = 0;
	} else {
		totalGridAmt = amount.value;
	}*/
	var totalGridAmt = 0;
	for ( var i = 1; i < rowCount; i++) {
		var totalAmt = getDomElementById("receivedAmounts" + i).value;
		if (!isEmptyAmt(totalAmt))
			totalGridAmt += parseFloat(totalAmt);
	}
	//totalGridAmt = parseFloat(totalGridAmt).toFixed(2);
	amount.value = parseFloat(totalGridAmt).toFixed(2);
	/*var value = obj.value;
	if (!isEmptyAmt(value)) {
		value = parseFloat(value).toFixed(2);
		value = parseFloat(value) + parseFloat(totalGridAmt);
		amount.value = parseFloat(value).toFixed(2);
	}*/
}


function calculateTotalAmountForCorrection(domObj, id) {
	if (!isNull(domObj.value) || id == 'D') {
		var total = 0;
		var rowNo = 0;
		var isCalcDedcReq = true;
		if (id == 'R') {
			rowNo = getRowId(domObj, "receivedAmounts");
		}
		if (id == 'T') {
			rowNo = getRowId(domObj, "tdsAmounts");
		}
		if (id == 'D') {
			rowNo = getRowId(domObj, "deduction");
			isCalcDedcReq = false;
		}
		if (!isEmptyAmt(document.getElementById("receivedAmounts" + rowNo).value)) {
			var rcvdAmt = document.getElementById("receivedAmounts" + rowNo);
			total = parseFloat(total) + parseFloat(rcvdAmt.value);
			rcvdAmt.value = parseFloat(rcvdAmt.value).toFixed(2);
			/*if (isCalcDedcReq) {
				calcDeductionAmt(rowNo);
			}*/
		}
		if (!isEmptyAmt(document.getElementById("tdsAmounts" + rowNo).value)) {
			var tdsAmt = document.getElementById("tdsAmounts" + rowNo);
			total = parseFloat(total) + parseFloat(tdsAmt.value);
			tdsAmt.value = parseFloat(tdsAmt.value).toFixed(2);
			/*if (isCalcDedcReq) {
				calcDeductionAmt(rowNo);
			}*/
		}
		if (!isEmptyAmt(document.getElementById("deduction" + rowNo).value)) {
			var deductionAmt = document.getElementById("deduction" + rowNo);
			if (deductionAmt.value == "-") {
				deductionAmt.value = "0.00";
			}
			total = parseFloat(total) + parseFloat(deductionAmt.value);
			deductionAmt.value = parseFloat(deductionAmt.value).toFixed(2);
		}
		document.getElementById("totals" + rowNo).value = total.toFixed(2);
		document.getElementById("totals" + rowNo).readOnly = true;
	}
}


function calculateHeaderAmtForCorection(obj) {
	var amount = getDomElementById("amount");
	var value = obj.value;
	if (!isEmptyAmt(value)) {
		amount.value = parseFloat(value).toFixed(2);
	}
}

function validateDeductionAmtForCorrection(obj, rowId) {
	if (obj.value == "-" || isNull(obj.value)) {
		obj.value = "0.00";
	}
	var totalAmt = getDomElementById("totals" + rowId);
	var billAmt = getDomElementById("billAmounts" + rowId);
	if (!isEmptyAmt(billAmt.value)) {
		calculateTotalAmountForCorrection(obj, 'D');
		if (parseFloat(totalAmt.value) > parseFloat(billAmt.value)
				|| isEmptyAmt(totalAmt.value) || parseFloat(totalAmt.value) < 0) {
			alert("Total amount should not greater than bill amount or zero or negative.");
			var rcvdAmt = getDomElementById("receivedAmounts" + rowId);
			obj.value = "";
			totalAmt.value = "";
			getDomElementById("tdsAmounts" + rowId).value = "";
			rcvdAmt.value = "";
			getDomElementById("amount").value = "";
			setTimeout(function() {
				rcvdAmt.focus();
			}, 10);
			return true;
		}
	}
	if (isEmptyAmt(obj.value)) {
		getDomElementById("reason" + rowId).disabled = true;
	} else {
		getDomElementById("reason" + rowId).disabled = false;
		getDomElementById("reason" + rowId).focus();
	}
}

function validateTDSAmtForCorrection(obj) {
	if (!isNull(obj.value)) {
		var rowId = getRowId(obj, "tdsAmounts");
		var billAmt = getDomElementById("billAmounts" + rowId);
		if (!isEmptyAmt(billAmt.value)) {
			billAmt.value = parseFloat(billAmt.value).toFixed(2);
			var totalAmt = getDomElementById("totals" + rowId);
			calculateTotalAmountForCorrection(obj, 'T');
			if (parseFloat(totalAmt.value) > parseFloat(billAmt.value)
					|| isEmptyAmt(totalAmt.value)
					|| parseFloat(totalAmt.value) < 0) {
				alert("Total amount should not greater than bill amount or zero or negative.");
				obj.value = "";
				totalAmt.value = "";
				getDomElementById("amount").value = "";
				getDomElementById("deduction" + rowId).value = "";
				getDomElementById("receivedAmounts" + rowId).value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return true;
			}
		}
		if (isEmptyAmt(obj.value)) {
			obj.value = 0.00;
		}
		obj.value = parseFloat(obj.value).toFixed(2);
	}
}

function validateRcvdAmtForCorrection(obj) {
	if (!isNull(obj.value)) {
		var rowId = getRowId(obj, "receivedAmounts");
		var billAmt = getDomElementById("billAmounts" + rowId);
		if (!isEmptyAmt(billAmt.value)) {
			billAmt.value = parseFloat(billAmt.value).toFixed(2);
			var totalAmt = getDomElementById("totals" + rowId);
			calculateTotalAmountForCorrection(obj, 'R');
			if (parseFloat(totalAmt.value) > parseFloat(billAmt.value)
					|| isEmptyAmt(totalAmt.value)
					|| parseFloat(totalAmt.value) < 0) {
				alert("Total amount should not greater than bill amount or zero or negative.");
				obj.value = "";
				totalAmt.value = "";
				getDomElementById("amount").value = "";
				getDomElementById("tdsAmounts" + rowId).value = "";
				getDomElementById("deduction" + rowId).value = "";
				setTimeout(function() {
					obj.focus();
				}, 10);
				return true;
			}
		} 
		if (isEmptyAmt(obj.value)) {
			obj.value = 0.00;
		}
		obj.value = parseFloat(obj.value).toFixed(2);
	}
}


function setDecimal(){
	getDomElementById("billAmounts1").value = (getDomElementById("billAmounts1").value).toFixed(2);
	getDomElementById("receivedAmounts1").value = (getDomElementById("receivedAmounts1").value).toFixed(2);
	getDomElementById("tdsAmounts1").value = (getDomElementById("tdsAmounts1").value).toFixed(2);
	getDomElementById("deduction1").value = (getDomElementById("deduction1").value).toFixed(2);
	getDomElementById("totals1").value = (getDomElementById("totals1").value).toFixed(2);
}