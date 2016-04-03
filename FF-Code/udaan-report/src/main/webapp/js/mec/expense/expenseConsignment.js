var rowCount = 1;
var eachRowAmount = new Array();
var ERROR_FLAG = "ERROR";

/**
 * Add row to grid table on page load
 */
function addRow() {
	$('#expenseEntryTable')
			.dataTable()
			.fnAddData(
					[
							rowCount,
							'<input type="text" name="to.consgNos" id="consgNos'
									+ rowCount
									+ '" class="txtbox width130" maxlength="14" onfocus="validateHeaderForGrid();" onblur="getConsignmentDtls(this);" onkeypress="return callEnterKey(event,document.getElementById(\'amounts'
									+ rowCount + '\'));">',
							'<input type="text" name="to.amounts" id="amounts'
									+ rowCount
									+ '" class="txtbox width70" maxlength="5" onfocus="validateHeaderForGrid();" onkeypress="return onlyNumberAndEnterKeyNav(event,this,\'otherCharges'
									+ rowCount
									+ '\');" onblur="setAmountFormat(this);setTotalAmtInGrid('
									+ rowCount + ');" />',
							'<input type="text" name="to.serviceCharges" id="serviceCharges'
									+ rowCount
									+ '" class="txtbox width70" maxlength="10" tabindex="-1" readonly="readonly" />',
							'<input type="text" name="to.serviceTaxs" id="serviceTaxs'
									+ rowCount
									+ '" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
							'<input type="text" name="to.eduCesss" id="eduCesss'
									+ rowCount
									+ '" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
							'<input type="text" name="to.higherEduCesss" id="higherEduCesss'
									+ rowCount
									+ '" class="txtbox width70" readonly="readonly" tabindex="-1"/>',
							'<input type="text" name="to.otherCharges" id="otherCharges'
									+ rowCount
									+ '" class="txtbox width70" maxlength="5" onfocus="validateHeaderForGrid();" onkeypress="return onlyNumberAndEnterKeyNav(event,this,\'remarks'
									+ rowCount
									+ '\');" onblur="setAmountFormatZero(this);setTotalAmtInGrid('
									+ rowCount + ');" />',
							'<input type="text" name="to.totals" id="totals'
									+ rowCount
									+ '" class="txtbox width70" maxlength="12" readonly="readonly" />',
							'<input type="text" name="to.remarks" id="remarks'
									+ rowCount
									+ '" class="txtbox width170" maxlength="50" onfocus="validateHeaderForGrid();" onkeypress="fnAddNewRow(event,this);" />\
		 <input type="hidden" name="to.consgIds" id="consgIds'
									+ rowCount
									+ '" />\
		 <input type="hidden" name="to.expenseEntriesIds" id="expenseEntriesIds'
									+ rowCount
									+ '" />\
		 <input type="hidden" name="to.positions" id="positions'
									+ rowCount + '" value="' + rowCount + '"/>', ]);
	// getDomElementById("consgNos"+rowCount).focus();
	rowCount++;
}

/**
 * To execute at page startup
 */
function expenseCNStartup() {
	validateForExpMode(getDomElementById("expenseMode").value);
	if (isNewExpenseEntry()) {
		addRow();
	} else {
		var expStatus = getDomElementById("expenseStatus").value;
		var isValidateScreen = getDomElementById("isValidateScreen").value;
		disableElement(getDomElementById("txNumber"));
		if (IS_CR_NT != CR_NT_YES) {
			var list = eval('(' + cnDtls + ')');
			for ( var i = 1; i <= parseFloat(NO_OF_ROWS); i++) {
				addRow();
				setCNRowDetails(list[i - 1], i);
			}
		}
		if (getDomElementById("expenseMode").value == EX_MODE_CHQ)
			enableOrDisableForExpMode(false);
		if (expStatus == STATUS_SUBMITTED || expStatus == STATUS_VALIDATED) {
			if (!isNull(isValidateScreen)
					&& isValidateScreen == IS_VALIDATE_YES) {
				enableOrDisableForValidate(true);
				if (IS_CR_NT != CR_NT_YES) {
					for ( var i = 1; i < rowCount; i++) {
						enableOrDisableCNGrid(i, true);
					}
				}
			} else {
				disableForSubmitted();
				getDomElementById("Cancel").focus();
			}
		} else {
			addRow();
		}
		setAmountFormat(getDomElementById("finalAmount"));
		if (IS_CR_NT == CR_NT_YES || expStatus == STATUS_OPENED) {
			getDomElementById("finalAmount").readOnly = false;
			if (expStatus != STATUS_OPENED) {
				getDomElementById("oldExpId").value = getDomElementById("expenseId").value;
				getDomElementById("expenseId").value = "";
			} else {
				var glPaymentType = getDomElementById("glPaymentType").value;
				if (glPaymentType == EX_MODE_CHQ
						|| glPaymentType == EX_MODE_CASH)
					getDomElementById("expenseMode").disabled = true;
			}
		} else {
			getDomElementById("finalAmount").readOnly = true;
		}
		setAmountFormat(getDomElementById("finalAmount"));
		enableFinalAmtForChqAndValidate();
		oldValue = getDomElementById("expenseType").value;
	}
}

/**
 * To check mandatory for add new row
 * 
 * @param rowId
 * @returns {Boolean}
 */
function checkMandatoryForAddRow(rowId) {
	var cnNo = getDomElementById("consgNos" + rowId);
	var amt = getDomElementById("amounts" + rowId);
	var remark = getDomElementById("remarks" + rowId);
	var total = getDomElementById("totals" + rowId);
	var lineNum = "at line :" + rowId;
	if (isNull(cnNo.value)) {
		alert("Please provide Consignment No. " + lineNum);
		setTimeout(function() {
			cnNo.focus();
		}, 10);
		return false;
	}
	if (isNull(amt.value)) {
		alert("Please provide Amount " + lineNum);
		setTimeout(function() {
			amt.focus();
		}, 10);
		return false;
	}
	if (isNull(total.value)) {
		alert("Please provide Total " + lineNum);
		setTimeout(function() {
			total.focus();
		}, 10);
		return false;
	}
	if (isNull(remark.value)) {
		alert("Please provide Remark " + lineNum);
		setTimeout(function() {
			remark.focus();
		}, 10);
		return false;
	}
	return true;
}

/**
 * The function to add new row
 * 
 * @param domElement
 */
function fnAddNewRow(e, domElement) {
	var key;
	if (window.event)
		key = window.event.keyCode; // IE
	else
		key = e.which; // firefox
	if (key == 13) {
		var currentRowId = getRowId(domElement, 'remarks');
		var tbl = document.getElementById('expenseEntryTable');
		var totalRowCount = tbl.rows.length - 1;
		if (parseInt(currentRowId) == totalRowCount) {
			if (checkMandatoryForAddRow(currentRowId)) {
				addRow();
				getDomElementById("consgNos" + (rowCount - 1)).focus();
			}
		} else {
			var nextRowId = parseInt(currentRowId) + 1;
			getDomElementById("consgNos" + nextRowId).focus();
		}
	}
}

/**
 * To save or update the expense details
 * 
 * @param action
 */
function saveOrUpdateExpenseDtls(action) {
	if (checkMandatoryForSave() && validateCNGrid()) {
		var expMode = getDomElementById("expenseMode").value;
		if (expMode != EX_MODE_CHQ) {
			getDomElementById("chequeDate").value = "";
		}
		if (action == 'save') {
			getDomElementById("expenseStatus").value = STATUS_OPENED;
		} else if (action == 'submit') {
			getDomElementById("expenseStatus").value = STATUS_SUBMITTED;
			if (!checkAmountBreakUp()) {
				return false;
			}
		}
		getDomElementById("expenseMode").disabled = false;
		var url = "./expenseEntry.do?submitName=saveOrUpdateExpenseDtls";
		submitForm(url, action);
	}
}

/**
 * To search the details from database
 */
function searchExpenseDtls() {
	var txNo = getDomElementById("txNumber");
	if (!isNull(txNo.value.trim())) {
		var url = "./expenseEntry.do?submitName=searchExpenseDtls";
		submitWithoutPrompt(url);
	} else {
		alert("Please provide Transaction Number");
		txNo.value = "";
		setTimeout(function() {
			txNo.focus();
		}, 10);
	}
}

/**
 * To clear screen
 * 
 * @param action
 */
function clearScreen(action) {
	var url = "./expenseEntry.do?submitName=viewExpenseConsignment";
	submitForm(url, action);
}

/**
 * To set consignment details to grid
 * 
 * @param list
 * @param rowId
 */
function setCNRowDetails(list, rowId) {
	jQuery("#consgNos" + rowId).val(list.consgNo);
	jQuery("#amounts" + rowId).val(list.amount);
	setAmountFormat(getDomElementById("amounts" + rowId));
	jQuery("#serviceCharges" + rowId).val(list.serviceCharge);
	setAmountFormatZero(getDomElementById("serviceCharges" + rowId));
	jQuery("#serviceTaxs" + rowId).val(list.serviceTax);
	setAmountFormatZero(getDomElementById("serviceTaxs" + rowId));
	jQuery("#eduCesss" + rowId).val(list.eduCess);
	setAmountFormatZero(getDomElementById("eduCesss" + rowId));
	jQuery("#higherEduCesss" + rowId).val(list.higherEduCess);
	setAmountFormatZero(getDomElementById("higherEduCesss" + rowId));
	jQuery("#otherCharges" + rowId).val(list.otherCharge);
	setAmountFormatZero(getDomElementById("otherCharges" + rowId));
	jQuery("#totals" + rowId).val(list.total);
	setAmountFormat(getDomElementById("totals" + rowId));
	jQuery("#remarks" + rowId).val(list.remark);
	jQuery("#expenseEntriesIds" + rowId).val(list.expenseEntriesId);
	jQuery("#consgIds" + rowId).val(list.consgId);
}

/**
 * To clear CN row details
 * 
 * @param rowId
 */
function clearCNRowDetails(rowId) {
	jQuery("#consgNos" + rowId).val("");
	jQuery("#amounts" + rowId).val("");
	jQuery("#serviceCharges" + rowId).val("");

	jQuery("#serviceTaxs" + rowId).val("");
	jQuery("#eduCesss" + rowId).val("");
	jQuery("#higherEduCesss" + rowId).val("");

	jQuery("#otherCharges" + rowId).val("");
	jQuery("#totals" + rowId).val("");
	jQuery("#remarks" + rowId).val("");

	jQuery("#expenseEntriesIds" + rowId).val("");// hidden
	jQuery("#consgIds" + rowId).val("");// hidden
}

/**
 * To get consignment details for expense
 * 
 * @param domElement
 */
function getConsignmentDtls(domElement) {
	var rowId = getRowId(domElement, "consgNos");
	ROW_ID = rowId;
	var domValue = domElement.value;
	var isOctroiGL = getDomElementById("isOctroiGL").value;
	var officeId = getDomElementById("loginOfficeId").value;
	if (!isNull(domValue) && isValidConsignment(domElement)
			&& !isDuplicateValInGrid(domElement, "consgNos")) {
		var url = "./expenseEntry.do?submitName=getConsignmentDtls"
				+ "&consgNo=" + domValue + "&officeId=" + officeId
				+ "&isOctroiGL=" + isOctroiGL;
		showProcessing();
		jQuery.ajax({
			url : url,
			success : function(req) {
				populateConsgDtls(req);
			}
		});
	} else {
		clearCNRowDetails(rowId);
	}
}

/**
 * To populate consignment details
 * 
 * @param ajaxResp
 */
function populateConsgDtls(ajaxResp) {
	if (!isNull(ajaxResp)) {
		var responsetext = jsonJqueryParser(ajaxResp);
		var error = responsetext[ERROR_FLAG];
		if (responsetext != null && error != null) {
			alert(error);
			clearCNRowDetails(ROW_ID);
			setTimeout(function() {
				getDomElementById("consgNos" + ROW_ID).focus();
			}, 10);
		} else {
			var data = eval('(' + ajaxResp + ')');
			var consgTO = data.consgTO;
			var cnRateCalcTO = data.cnRateCalcOutputTO;

			clearCNRowDetails(ROW_ID);
			jQuery("#consgIds" + ROW_ID).val(consgTO.consgId);
			jQuery("#consgNos" + ROW_ID).val(consgTO.consgNo);

			/** Set Calculated Rate for consignment */
			if (!isNull(cnRateCalcTO)) {
				jQuery("#serviceCharges" + ROW_ID).val(
						cnRateCalcTO.octroiServiceCharge);
				
				jQuery("#serviceTaxs" + ROW_ID).val(
						cnRateCalcTO.serviceTaxOnOctroiServiceCharge);// serviceTax
				jQuery("#eduCesss" + ROW_ID).val(
						cnRateCalcTO.eduCessOnOctroiServiceCharge);// educationCess
				jQuery("#higherEduCesss" + ROW_ID).val(
						cnRateCalcTO.higherEduCessOnOctroiServiceCharge);// higherEducationCess
				
				jQuery("#serviceTaxs" + ROW_ID).val(cnRateCalcTO.serviceTax);
				jQuery("#eduCesss" + ROW_ID).val(cnRateCalcTO.educationCess);
				jQuery("#higherEduCesss" + ROW_ID).val(
						cnRateCalcTO.higherEducationCess);
			}

			setAmountFormatZero(getDomElementById("serviceCharges" + ROW_ID));
			setAmountFormatZero(getDomElementById("serviceTaxs" + ROW_ID));
			setAmountFormatZero(getDomElementById("eduCesss" + ROW_ID));
			setAmountFormatZero(getDomElementById("higherEduCesss" + ROW_ID));
			setTotalAmtInGrid(ROW_ID);
		}
	} else {
		alert("Invalid Consignment Number.");
		clearCNRowDetails(ROW_ID);
		getDomElementById("consgNos" + ROW_ID).focus();
	}
	hideProcessing();
}

/**
 * To enable or disable CN grid's row(s)
 * 
 * @param rowId
 * @param flag
 */
function enableOrDisableCNGrid(rowId, flag) {
	getDomElementById("consgNos" + rowId).disabled = flag;
	getDomElementById("amounts" + rowId).disabled = flag;
	getDomElementById("serviceCharges" + rowId).disabled = flag;
	getDomElementById("otherCharges" + rowId).disabled = flag;
	getDomElementById("totals" + rowId).disabled = flag;
	getDomElementById("remarks" + rowId).disabled = flag;
}

/**
 * To set Total amount in grid
 * 
 * @param rowId
 */
function setTotalAmtInGrid(rowId) {
	var amt = getDomElementById("amounts" + rowId).value;
	var sChrg = getDomElementById("serviceCharges" + rowId).value;
	var sTax = getDomElementById("serviceTaxs" + rowId).value;
	var eduCessTax = getDomElementById("eduCesss" + rowId).value;
	var hEduCessTax = getDomElementById("higherEduCesss" + rowId).value;
	var oChrg = getDomElementById("otherCharges" + rowId).value;

	amt = (isNull(amt)) ? 0 : parseFloat(amt) * 100;
	sChrg = (isNull(sChrg)) ? 0 : parseFloat(sChrg) * 100;
	sTax = (isNull(sTax)) ? 0 : parseFloat(sTax) * 100;
	eduCessTax = (isNull(eduCessTax)) ? 0 : parseFloat(eduCessTax) * 100;
	hEduCessTax = (isNull(hEduCessTax)) ? 0 : parseFloat(hEduCessTax) * 100;
	oChrg = (isNull(oChrg)) ? 0 : parseFloat(oChrg) * 100;

	var totAmt = amt + sChrg + sTax + eduCessTax + hEduCessTax + oChrg;
	totAmt /= 100;
	jQuery("#totals" + rowId).val(totAmt);
	setAmountFormatZero(getDomElementById("totals" + rowId));
}

/**
 * To validate CN grid
 * 
 * @returns {Boolean}
 */
function validateCNGrid() {
	for ( var i = 1; i < rowCount; i++) {
		if (i == 1 || !isNull(getDomElementById("consgNos" + i).value)) {
			if (!validateSelectRow(i)) {
				return false;
			}
		}
	}
	return true;
}

/**
 * To validate CN row by its row id
 * 
 * @param rowId
 */
function validateSelectRow(rowId) {
	var consgNo = getDomElementById("consgNos" + rowId);
	var amt = getDomElementById("amounts" + rowId);
	var total = getDomElementById("totals" + rowId);
	var remark = getDomElementById("remarks" + rowId);
	var atLine = " at Line:" + rowId;
	if (isNull(consgNo.value)) {
		alert("Please provide Consignment No." + atLine);
		setTimeout(function() {
			consgNo.focus();
		}, 10);
		return false;
	}
	if (isNull(amt.value)) {
		alert("Please provide Amount" + atLine);
		setTimeout(function() {
			amt.focus();
		}, 10);
		return false;
	}
	if (isNull(total.value)) {
		alert("Please provide Total" + atLine);
		setTimeout(function() {
			total.focus();
		}, 10);
		return false;
	}
	if (isNull(remark.value)) {
		alert("Please provide Remark" + atLine);
		setTimeout(function() {
			remark.focus();
		}, 10);
		return false;
	}
	return true;
}